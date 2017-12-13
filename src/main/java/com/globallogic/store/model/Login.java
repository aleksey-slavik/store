package com.globallogic.store.model;

/**
 * Data from the login page
 *
 * @author oleksii.slavik
 */
public class Login {

    /**
     * Entered username
     */
    private String username;

    /**
     * Entered password
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Login{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
