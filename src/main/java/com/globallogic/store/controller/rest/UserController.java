package com.globallogic.store.controller.rest;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;

import java.util.List;

@RestController
public class UserController {

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@RequestParam Long id) {
        ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        AbstractGenericDAO userDao = (AbstractGenericDAO) context.getBean("userDao");
        return (User) userDao.findById(id);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUserList() {
        ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        AbstractGenericDAO userDao = (AbstractGenericDAO) context.getBean("userDao");
        return userDao.findAll();
    }

    @RequestMapping(value = "/create-user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Long createUser(@RequestBody User user) {
        ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        AbstractGenericDAO userDao = (AbstractGenericDAO) context.getBean("userDao");
        return userDao.create(user);
    }

    @RequestMapping(value = "/update-user", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody User user) {
        ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        AbstractGenericDAO userDao = (AbstractGenericDAO) context.getBean("userDao");
        userDao.update(user);
    }

    @RequestMapping(value = "/delete-user", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUserById(@RequestParam Long id) {
        ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        AbstractGenericDAO userDao = (AbstractGenericDAO) context.getBean("userDao");
        userDao.delete(id);
    }
}
