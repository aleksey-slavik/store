package com.globallogic.store.rest.spring;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.exception.EmptyResponseException;
import com.globallogic.store.exception.NotFoundException;
import com.globallogic.store.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Spring rest controller for {@link User}.
 *
 * @author oleksii.slavik
 */
@RestController
public class UserSpringRestController {

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
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public User findUserById(@PathVariable Long id) {
        User user = userDao.entityByKey(id);

        if (user != null) {
            return user;
        }

        throw new NotFoundException();
    }

    /**
     * Return list of {@link User} items result of search by given parameters.
     *
     * @param params given parameters
     * @return list of {@link User}
     * @throws NotFoundException      throws when user with given id not found
     * @throws EmptyResponseException throws when user list is empty
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<User> findUser(@RequestParam MultiValueMap<String, String> params) {
        List<User> users = null;

        if (params.isEmpty()) {
            users = userDao.entityList();
        } else {

        }

        if (users == null) {
            throw new NotFoundException();
        }

        if (users.isEmpty()) {
            throw new EmptyResponseException();
        }

        return users;
    }

    /**
     * Create given {@link User}
     *
     * @param user given {@link User}
     * @return created {@link User}
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user) {
        return userDao.createEntity(user);
    }

    /**
     * Update {@link User} item with given id
     *
     * @param id   given id of {@link User}
     * @param user updated {@link User} data
     * @return updated {@link User}
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return userDao.updateEntity(user);
    }

    /**
     * Delete {@link User} item with given id
     *
     * @param id given id of {@link User}
     * @return deleted {@link User}
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User deleteUserById(@PathVariable Long id) {
        return userDao.deleteEntity(id);
    }
}
