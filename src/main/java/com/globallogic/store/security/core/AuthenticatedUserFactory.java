package com.globallogic.store.security.core;

import com.globallogic.store.domain.user.Authority;
import com.globallogic.store.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class for create user details
 *
 * @author oleksii.slavik
 */
public class AuthenticatedUserFactory {

    private AuthenticatedUserFactory() {
    }

    /**
     * Create authenticated user details from given user data
     *
     * @param user given user data
     * @return authenticated user details
     */
    public static AuthenticatedUser create(User user) {
        return new AuthenticatedUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                createAuthorityList(user.getAuthorities()),
                user.isEnabled()
        );
    }

    /**
     * Create authority list from user data
     *
     * @param authorities given authorities from user data
     * @return created authority list
     */
    private static List<GrantedAuthority> createAuthorityList(Set<Authority> authorities) {
        List<GrantedAuthority> result = new ArrayList<>();

        for (Authority authority : authorities) {
            result.add(createAuthority(authority));
        }

        return result;
    }

    /**
     * Create granted authority
     *
     * @see GrantedAuthority
     * @see Authority
     * @param authority given authority
     * @return granted authority
     */
    private static GrantedAuthority createAuthority(Authority authority) {
        return new SimpleGrantedAuthority(authority.getTitle().name());
    }
}
