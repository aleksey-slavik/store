package com.globallogic.store.rest;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {

    private AbstractGenericDAO<User> userDao;

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable Long id) {
        return userDao.findById(id);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUserList() {
        return userDao.findAll();
    }

    @RequestMapping(value = "/users/{username}/{password}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> verifyUser(@PathVariable String username, @PathVariable String password) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        return userDao.findByCriteria(params);
    }

    @RequestMapping(value = "/users/search/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> searchUser(@PathVariable String key) {
        return userDao.findByKey(key, "firstname", "lastname", "username");
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user) {
        return userDao.create(user);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return userDao.update(user);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User deleteUserById(@PathVariable Long id) {
        return userDao.delete(id);
    }

    public void setUserDao(AbstractGenericDAO<User> userDao) {
        this.userDao = userDao;
    }
}
