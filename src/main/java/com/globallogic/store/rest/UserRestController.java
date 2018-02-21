package com.globallogic.store.rest;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.SearchCriteria;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.exception.EmptyResponseException;
import com.globallogic.store.exception.NotAcceptableException;
import com.globallogic.store.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Spring rest controller for {@link User}.
 *
 * @author oleksii.slavik
 */
@RestController
@RequestMapping(value = "/api/users")
public class UserRestController {

    @Value("${user.username}")
    private String usernameKey;

    /**
     * {@link User} DAO object for access to database.
     */
    private GenericDao<User> userDao;

    /**
     * Injection {@link User} DAO object for access to database.
     */
    public void setUserDao(GenericDao<User> userDao) {
        this.userDao = userDao;
    }

    /**
     * Return {@link User} item with given id
     *
     * @param id given id
     * @return {@link User} item
     * @throws NotFoundException throws when user with given id not found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userDao.getEntityByKey(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok(user);
        }
    }

    /**
     * Return list of {@link User} items result of search by given parameters.
     *
     * @param username username of user
     * @param password password of user
     * @return list of {@link User}
     * @throws NotFoundException      throws when user with given id not found
     * @throws EmptyResponseException throws when user list is empty
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUser(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "order", defaultValue = "asc") String order) {

        SearchCriteria criteria = new SearchCriteria(page, size, sort, order);
        criteria.addCriteria("username", username);
        criteria.addCriteria("password", password);
        List<User> users = userDao.getEntityList(criteria);


        if (users == null || users.isEmpty()) {
            throw new NotFoundException();
        }

        return users;
    }

    /**
     * Create given {@link User}
     *
     * @param user given {@link User}
     * @return created {@link User}
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user) {
        checkUser(user);
        return userDao.createEntity(user);
    }

    /**
     * Update {@link User} item with given id
     *
     * @param id   given id of {@link User}
     * @param user updated {@link User} data
     * @return updated {@link User}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@PathVariable Long id, final @RequestBody User user) {
        checkUser(user);
        user.setId(id);
        return userDao.updateEntity(user);
    }

    /**
     * Delete {@link User} item with given id
     *
     * @param id given id of {@link User}
     * @return deleted {@link User}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User deleteUserById(@PathVariable Long id) {
        getUserById(id);
        return userDao.deleteEntity(id);
    }

    private void checkUser(User user) {
        SearchCriteria criteria = new SearchCriteria();
        criteria.addCriteria("username", user.getUsername());
        User checkUser = userDao.getEntity(criteria);

        if (checkUser != null) {
            throw new NotAcceptableException();
        }
    }
}
