package com.globallogic.store.security;

import com.globallogic.store.domain.user.Authority;
import com.globallogic.store.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AuthenticatedUserFactory {

    private AuthenticatedUserFactory() {
    }

    public static AuthenticatedUser create(User user) {
        return new AuthenticatedUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                createAuthorityList(user.getAuthorities()),
                user.isEnabled()
        );
    }

    private static List<GrantedAuthority> createAuthorityList(Set<Authority> authorities) {
        List<GrantedAuthority> result = new ArrayList<>();

        for (Authority authority : authorities) {
            result.add(createAuthority(authority));
        }

        return result;
    }

    private static GrantedAuthority createAuthority(Authority authority) {
        return new SimpleGrantedAuthority(authority.getTitle().name());
    }
}
