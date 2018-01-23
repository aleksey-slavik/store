package com.globallogic.store.security.spring;

import com.globallogic.store.dao.AbstractDAO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

/**
 * Implementation of {@link UserDetailsService} interface.
 *
 * @author oleksii.slavik
 */
public class LoginUserService implements UserDetailsService {

    /**
     * {@link AbstractDAO} object to access {@link com.globallogic.store.model.User} DAO methods.
     */
    private AbstractDAO userDao;

    /**
     * Injection {@link AbstractDAO} object to access {@link com.globallogic.store.model.User} DAO methods.
     */
    public void setUserDao(AbstractDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * Create {@link UserDetails} object of {@link com.globallogic.store.model.User} with given username.
     *
     * @param s given username
     * @return {@link UserDetails} object
     * @throws UsernameNotFoundException throws when {@link com.globallogic.store.model.User} with given username is not found
     */
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", s);
        com.globallogic.store.model.User user = (com.globallogic.store.model.User) userDao.exactSearch(params);
        List<GrantedAuthority> authorities = buildUserAuthority(user);
        System.out.println(authorities);
        return buildUserForAuthentication(user, authorities);
    }

    /**
     * Create {@link User} object using {@link com.globallogic.store.model.User} data and list of {@link GrantedAuthority}.
     *
     * @param user        given {@link com.globallogic.store.model.User} data
     * @param authorities given list of {@link GrantedAuthority}
     * @return created {@link User}
     */
    private User buildUserForAuthentication(com.globallogic.store.model.User user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    /**
     * Create list of {@link GrantedAuthority} using {@link com.globallogic.store.model.User} data.
     *
     * @param user given {@link com.globallogic.store.model.User} data
     * @return created list of {@link GrantedAuthority}
     */
    private List<GrantedAuthority> buildUserAuthority(com.globallogic.store.model.User user) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new ArrayList<GrantedAuthority>(authorities);
    }
}
