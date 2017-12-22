package com.globallogic.store.service;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

public class UserService implements UserDetailsService {

    private AbstractGenericDAO userDao;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", s);
        com.globallogic.store.model.User user = (com.globallogic.store.model.User) userDao.findByCriteria(params);
        List<GrantedAuthority> authorities = buildUserAuthority();
        return buildUserForAuthentication(user, authorities);
    }

    private User buildUserForAuthentication(com.globallogic.store.model.User user,
                                            List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    private List<GrantedAuthority> buildUserAuthority() {
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        for (Role userRole : Role.values()) {
            setAuths.add(new SimpleGrantedAuthority(userRole.name()));
        }

        return new ArrayList<GrantedAuthority>(setAuths);
    }

    public void setUserDao(AbstractGenericDAO userDao) {
        this.userDao = userDao;
    }
}
