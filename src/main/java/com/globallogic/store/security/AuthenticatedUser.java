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
}
