package com.globallogic.store.security.dto;

public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse() {
        super();
    }

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
