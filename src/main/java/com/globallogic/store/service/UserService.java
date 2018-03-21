package com.globallogic.store.service;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.criteria.SearchCriteria;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    /**
     * user DAO
     */
    private GenericDao<User> userDao;

    public UserService(GenericDao<User> userDao) {
        this.userDao = userDao;
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
        SearchCriteria ownerCriteria = new SearchCriteria().criteria("username", username);
        User user = userDao.getEntity(ownerCriteria);

        if (user == null) {
            throw new NotFoundException("User with username=" + username + " not found!");
        } else {
            return user;
        }
    }
}
