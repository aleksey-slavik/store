package com.globallogic.store.rest;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.model.Role;
import com.globallogic.store.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable Long id) {
        AbstractGenericDAO<User> userDao = new AbstractGenericDAO<User>(User.class);
        return userDao.findById(id);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUserList() {
        AbstractGenericDAO<User> userDao = new AbstractGenericDAO<User>(User.class);
        return userDao.findAll();
    }

    @RequestMapping(value = "/create-user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Long createUser(@RequestBody User user) {
        AbstractGenericDAO<User> userDao = new AbstractGenericDAO<User>(User.class);
        user.setRole(Role.CLIENT);
        return userDao.create(user);
    }

    @RequestMapping(value = "/update-user", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody User user) {
        AbstractGenericDAO<User> userDao = new AbstractGenericDAO<User>(User.class);
        userDao.update(user);
    }

    @RequestMapping(value = "/delete-user/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUserById(@PathVariable Long id) {

    }
}
