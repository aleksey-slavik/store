package com.globallogic.store.dto.user;

import com.globallogic.store.domain.user.User;

import javax.validation.constraints.*;

public class UserDto {

    @NotNull
    @Size(min = 6, max = 50)
    private String username;

    @NotNull
    @Size(min = 6, max = 50)
    private String password;

    @NotNull
    @Size(min = 6, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 6, max = 50)
    private String lastName;

    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]+$")
    private String email;

    public UserDto() {}

    public UserDto(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UserDto(User user) {
        this(
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail());
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
}
