package com.globallogic.store.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class AuthenticatedUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final GrantedAuthority authority;
    private final boolean isEnabled;

    public AuthenticatedUser(Long id, String username, String password, GrantedAuthority authority, boolean isEnabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authority = authority;
        this.isEnabled = isEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(authority);
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

    public GrantedAuthority getAuthority() {
        return authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthenticatedUser user = (AuthenticatedUser) o;

        if (isEnabled != user.isEnabled) return false;
        if (!id.equals(user.id)) return false;
        if (!username.equals(user.username)) return false;
        if (!password.equals(user.password)) return false;
        return authority.equals(user.authority);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + authority.hashCode();
        result = 31 * result + (isEnabled ? 1 : 0);
        return result;
    }
}
