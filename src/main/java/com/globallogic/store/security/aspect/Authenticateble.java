package com.globallogic.store.security.aspect;

import org.springframework.security.core.Authentication;

public interface Authenticateble {

    Authentication getAuthentication();
}
