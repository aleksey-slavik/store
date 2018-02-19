package com.globallogic.store.security;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.domain.user.Authority;
import com.globallogic.store.security.AuthenticatedUserFactory;
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
     * {@link GenericDao} object to access {@link com.globallogic.store.domain.user.User} DAO methods.
     */
    private GenericDao userDao;

    /**
     * Injection {@link GenericDao} object to access {@link com.globallogic.store.domain.user.User} DAO methods.
     */
    public void setUserDao(GenericDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Create {@link UserDetails} object of {@link com.globallogic.store.domain.user.User} with given username.
     *
     * @param s given username
     * @return {@link UserDetails} object
     * @throws UsernameNotFoundException throws when {@link com.globallogic.store.domain.user.User} with given username is not found
     */
    public UserDetails loadUserByUsername(final String s) throws UsernameNotFoundException {
        com.globallogic.store.domain.user.User user = (com.globallogic.store.domain.user.User) userDao.entityByValue(
                new HashMap<String, String>() {{
                    put("username", s);
                }});

        /*if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'", s));
        } else {
            return AuthenticatedUserFactory.create(user);
        }*/

        List<GrantedAuthority> authorities = buildUserAuthority(user);
        System.out.println(authorities);
        return buildUserForAuthentication(user, authorities);
    }

    /**
     * Create {@link User} object using {@link com.globallogic.store.domain.user.User} data and list of {@link GrantedAuthority}.
     *
     * @param user        given {@link com.globallogic.store.domain.user.User} data
     * @param authorities given list of {@link GrantedAuthority}
     * @return created {@link User}
     */
    private User buildUserForAuthentication(com.globallogic.store.domain.user.User user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    /** Create list of {@link GrantedAuthority} using {@link com.globallogic.store.domain.user.User} data.
     *
     * @param user given {@link com.globallogic.store.domain.user.User} data
     * @return created list of {@link GrantedAuthority}
     */
    private List<GrantedAuthority> buildUserAuthority(com.globallogic.store.domain.user.User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Authority authority : user.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(authority.getTitle().name()));
        }
        return new ArrayList<>(authorities);
    }
}
