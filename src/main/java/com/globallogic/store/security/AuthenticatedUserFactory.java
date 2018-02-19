package com.globallogic.store.security;

import com.globallogic.store.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AuthenticatedUserFactory {

    private AuthenticatedUserFactory() {
    }

    public static AuthenticatedUser create(User user) {
        return new AuthenticatedUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                null,
                true
        );
    }

   /* private static GrantedAuthority createAuthority(Role role) {
        return new SimpleGrantedAuthority(role.name());
    }*/
}
