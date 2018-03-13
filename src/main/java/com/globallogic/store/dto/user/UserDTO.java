package com.globallogic.store.dto.user;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

    private long userId;

    @NotNull(message = "Username cannot be null")
    @Size(max = 30, message = "Username must be less than {max} characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(max = 30, message = "Password must be less than {max} characters")
    private String password;

    @NotNull(message = "First name of user cannot be null")
    @Size(max = 30, message = "First name of user must be less than {max} characters")
    private String firstName;

    @NotNull(message = "Last name of user cannot be null")
    @Size(max = 30, message = "Last name of user must be less than {max} characters")
    private String lastName;

    @NotNull(message = "Email of user cannot be null")
    @Email(message = "Not valid email of user")
    private String email;

    private boolean enabled;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
