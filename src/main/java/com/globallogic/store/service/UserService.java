package com.globallogic.store.service;

import com.globallogic.store.dao.UserDAO;

public class UserService {
    private UserDAO userDao;

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    public UserDAO getUserDao() {
        return userDao;
    }
}
