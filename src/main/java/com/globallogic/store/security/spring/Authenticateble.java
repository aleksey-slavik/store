package com.globallogic.store.security.spring;

import org.springframework.security.core.Authentication;

public interface Authenticateble {

    Authentication getAuthentication();
}
