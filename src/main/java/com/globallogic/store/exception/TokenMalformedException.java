package com.globallogic.store.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenMalformedException extends AuthenticationException {

    public TokenMalformedException(String message) {
        super(message);
    }
}
