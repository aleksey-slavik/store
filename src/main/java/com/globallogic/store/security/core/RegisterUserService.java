package com.globallogic.store.security.core;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Implementation of {@link Registrable} interface.
 * Security service of auto login during registration.
 *
 * @author oleksii.slavik
 */
public class RegisterUserService implements Registrable {

    /**
     * {@link UserDetailsService} object for access to security login.
     */
    private UserDetailsService userDetailsService;

    /**
     * {@link AuthenticationManager} object.
     */
    private AuthenticationManager authenticationManager;

    /**
     * Injection {@link UserDetailsService} object for access to security login.
     */
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Injection {@link AuthenticationManager} object.
     */
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Login user with given username and password.
     *
     * @param username given username
     * @param password given password
     */
    public void autoLogin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(authenticationToken);

        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
