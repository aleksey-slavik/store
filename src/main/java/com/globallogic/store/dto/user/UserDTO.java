package com.globallogic.store.dto.user;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User data transfer object
 *
 * @author oleksii.slavik
 */
public class UserDTO {

    /**
     * user id
     */
    private long id;

    /**
     * user username
     */
    @NotNull(message = "Username cannot be null")
    @Size(max = 30, message = "Username must be less than {max} characters")
    private String username;

    /**
     * user password
     */
    @NotNull(message = "Password cannot be null")
    @Size(max = 30, message = "Password must be less than {max} characters")
    private String password;

    /**
     * user first name
     */
    @NotNull(message = "First name of user cannot be null")
    @Size(max = 30, message = "First name of user must be less than {max} characters")
    private String firstName;

    /**
     * user last name
     */
    @NotNull(message = "Last name of user cannot be null")
    @Size(max = 30, message = "Last name of user must be less than {max} characters")
    private String lastName;

    /**
     * user email
     */
    @NotNull(message = "Email of user cannot be null")
    @Email(message = "Not valid email of user")
    private String email;

    /**
     * state of user account
     */
    private boolean enabled;

    /**
     * @return user id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id user id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return username of user
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username username of user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return user first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName user first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return user last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName user last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return true if user account is active, false in otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled state of user account
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
