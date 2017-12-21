package com.globallogic.store.service;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService implements UserDetailsService {

    private AbstractGenericDAO userDao;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    public void setUserDao(AbstractGenericDAO userDao) {
        this.userDao = userDao;
    }
}
