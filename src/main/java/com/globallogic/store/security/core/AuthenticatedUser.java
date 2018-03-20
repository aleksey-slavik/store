package com.globallogic.store.security.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Custom user details
 *
 * @author oleksii.slavik
 */
public class AuthenticatedUser implements UserDetails {

    /**
     * user id
     */
    private final Long id;

    /**
     * username of user
     */
    private final String username;

    /**
     * user password
     */
    private final String password;

    /**
     * granted user authorities
     */
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * statement of user
     */
    private final boolean isEnabled;

    public AuthenticatedUser(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, boolean isEnabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isEnabled = isEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthenticatedUser user = (AuthenticatedUser) o;

        return isEnabled == user.isEnabled &&
                id.equals(user.id) &&
                username.equals(user.username) &&
                password.equals(user.password) &&
                authorities.equals(user.authorities);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + authorities.hashCode();
        result = 31 * result + (isEnabled ? 1 : 0);
        return result;
    }
}
