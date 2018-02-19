package com.globallogic.store.security;

/**
 * Interface which consist method of auto login during success registration.
 *
 * @author oleksii.slavik
 */
public interface Registrable {

    /**
     * Login user with given username and password.
     *
     * @param username given username
     * @param password given password
     */
    void autoLogin(String username, String password);
}
