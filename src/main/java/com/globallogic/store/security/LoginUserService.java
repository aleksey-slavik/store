package com.globallogic.store.security;

import com.globallogic.store.dao.AbstractGenericDAO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

public class LoginUserService implements UserDetailsService {

    private AbstractGenericDAO userDao;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", s);
        com.globallogic.store.model.User user = (com.globallogic.store.model.User) userDao.findByCriteria(params).get(0);
        List<GrantedAuthority> authorities = buildUserAuthority(user);
        return buildUserForAuthentication(user, authorities);
    }

    private User buildUserForAuthentication(com.globallogic.store.model.User user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(com.globallogic.store.model.User user) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new ArrayList<GrantedAuthority>(authorities);
    }

    public void setUserDao(AbstractGenericDAO userDao) {
        this.userDao = userDao;
    }
}
