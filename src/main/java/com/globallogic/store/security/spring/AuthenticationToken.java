package com.globallogic.store.security.spring;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String token;

    public AuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
