package com.globallogic.store.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenMissingException extends AuthenticationException {

    public TokenMissingException(String message) {
        super(message);
    }
}
