package com.globallogic.store.service;

import com.globallogic.store.converter.user.UserConverter;
import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.criteria.SearchCriteria;
import com.globallogic.store.domain.user.Authority;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.dto.user.AuthorityDTO;
import com.globallogic.store.dto.user.UserDTO;
import com.globallogic.store.exception.AlreadyExistException;
import com.globallogic.store.exception.NoContentException;
import com.globallogic.store.exception.NotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    /**
     * user DAO
     */
    private GenericDao<User> userDao;

    private AuthorityService authorityService;

    private UserConverter userConverter;

    public UserService(
            GenericDao<User> userDao,
            AuthorityService authorityService,
            UserConverter userConverter) {
        this.userDao = userDao;
        this.authorityService = authorityService;
        this.userConverter = userConverter;
    }

    public User getById(long id) throws NotFoundException {
        User user = userDao.getEntityByKey(id);

        if (user == null) {
            throw new NotFoundException("User with id=" + id + " not found!");
        } else {
            return user;
        }
    }

    public User getByUsername(String username) throws NotFoundException {
        try {
            SearchCriteria criteria = new SearchCriteria().criteria("username", username);
            return userDao.getEntity(criteria);
        } catch (NoResultException e) {
            throw new NotFoundException("User with username=" + username + " not found!");
        }
    }

    public List<User> getList(SearchCriteria criteria) throws NoContentException {
        List<User> products = userDao.getEntityList(criteria);

        if (products == null || products.isEmpty()) {
            throw new NoContentException("Users with given criteria not found!");
        } else {
            return products;
        }
    }

    public User insert(UserDTO user) throws AlreadyExistException, NotFoundException {
        try {
            checkUser(user.getId(), user.getUsername());
            throw new AlreadyExistException("User " + user.getUsername() + " already exist!");
        } catch (NotFoundException e) {
            User created = userDao.createEntity(userConverter.toOrigin(user));
            Authority customer = authorityService.getByTitle("CUSTOMER");
            created.appendAuthority(customer);
            created.setEnabled(true);
            return userDao.updateEntity(created);
        }
    }

    public User update(long id, UserDTO user) throws NotFoundException {
        User updated = getById(id);
        Set<Authority> authorities = updated.getAuthorities();
        user.setId(id);
        updated = userConverter.toOrigin(user);
        updated.setAuthorities(authorities);
        return userDao.updateEntity(updated);
    }

    public User remove(long id) throws NotFoundException {
        User deleted = getById(id);
        return userDao.deleteEntity(deleted);
    }

    private User checkUser(long id, String username) throws NotFoundException {
        try {
            SearchCriteria criteria = new SearchCriteria()
                    .criteria("id", id)
                    .criteria("username", username);

            return userDao.getEntity(criteria);
        } catch (NoResultException e) {
            throw new NotFoundException("User with id=" + id + " and username=" + username + " not found!");
        }
    }

    public List<Authority> getUserAuthorities(long id) throws NotFoundException {
        User user = getById(id);
        List<Authority> authorities = new ArrayList<>();
        authorities.addAll(user.getAuthorities());
        return authorities;
    }

    public Authority appendAuthority(long id, AuthorityDTO authority) throws NotFoundException {
        User user = getById(id);
        Authority granted = authorityService.getByTitle(authority.getTitle().name());
        user.appendAuthority(granted);
        userDao.updateEntity(user);
        return granted;
    }

    public Authority removeAuthority(long userId, long authId) throws NotFoundException {
        User user = getById(userId);
        Authority removed = authorityService.getById(authId);
        user.removeAuthority(removed);
        userDao.updateEntity(user);
        return removed;
    }
}
