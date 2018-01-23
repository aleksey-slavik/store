package com.globallogic.store.rest.spring;

import com.globallogic.store.dao.AbstractDAO;
import com.globallogic.store.exception.EmptyResponseException;
import com.globallogic.store.exception.NotFoundException;
import com.globallogic.store.model.User;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Spring rest controller for {@link User}.
 *
 * @author oleksii.slavik
 */
@RestController
public class UserRestController {

    /**
     * {@link User} DAO object for access to database.
     */
    private AbstractDAO<User> userDao;

    /**
     * Injection {@link User} DAO object for access to database.
     */
    public void setUserDao(AbstractDAO<User> userDao) {
        this.userDao = userDao;
    }

    /**
     * Return {@link User} item with given id
     *
     * @param id given id
     * @return {@link User} item
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable Long id) {
        return userDao.findById(id);
    }

    /**
     * Return list of {@link User} items result of search by given parameters.
     * If parameter list throws {@link EmptyResponseException}.
     *
     * @param params given parameters
     * @return list of {@link User}
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> findUser(@RequestParam MultiValueMap<String, String> params) {
        if (params.isEmpty()) {
            throw new EmptyResponseException();
        }

        List<User> users;

        if (params.containsKey("all")) {
            users = userDao.findAll();
        } else if (params.containsKey("query")) {
            String queryKey = params.getFirst("query");
            users = userDao.fuzzySearch(queryKey, "name", "firstname", "lastname");
        } else {
            users = Collections.singletonList(userDao.exactSearch(params.toSingleValueMap()));
        }

        if (users == null || users.isEmpty()) {
            throw new NotFoundException();
        } else {
            return users;
        }
    }

    /**
     * Create given {@link User}
     *
     * @param user given {@link User}
     * @return created {@link User}
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user) {
        return userDao.create(user);
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
        return userDao.update(user);
    }

    /**
     * Delete {@link User} item with given id
     *
     * @param id given id of {@link User}
     * @return deleted {@link User}
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User deleteUserById(@PathVariable Long id) {
        return userDao.delete(id);
    }
}
