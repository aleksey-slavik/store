package com.globallogic.store.security.service;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.criteria.SearchCriteria;
import com.globallogic.store.security.core.AuthenticatedUserFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
        SearchCriteria criteria = new SearchCriteria();
        criteria.criteria("username", s);
        com.globallogic.store.domain.user.User user = (com.globallogic.store.domain.user.User) userDao.getEntity(criteria);
        return AuthenticatedUserFactory.create(user);
    }
}
