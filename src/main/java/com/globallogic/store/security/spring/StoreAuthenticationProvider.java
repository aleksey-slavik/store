package com.globallogic.store.security.spring;

import com.globallogic.store.dao.AbstractDAO;
import com.globallogic.store.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreAuthenticationProvider implements AuthenticationProvider {

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

    public StoreAuthenticationProvider() {
        super();
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        User user = (User) userDao.findByCriteria(params).get(0);

        if (user != null) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, password, authorities);
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            //SecurityContextHolder.getContext().setAuthentication(auth);
            return auth;
        } else {
            return null;//todo throws
        }
    }

    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
