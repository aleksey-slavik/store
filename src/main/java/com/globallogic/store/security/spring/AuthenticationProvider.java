package com.globallogic.store.security.spring;

import com.globallogic.store.exception.TokenMalformedException;
import com.globallogic.store.model.User;
import com.globallogic.store.security.jwt.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        AuthenticationToken authenticationToken = (AuthenticationToken) authentication;
        String token = authenticationToken.getToken();
        User parsedUser = tokenUtil.parseToken(token);

        if (parsedUser == null) {
            throw new TokenMalformedException("JWT token is not valid");
        }

        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole().name());
        return new AuthenticatedUser(parsedUser.getId(), parsedUser.getUsername(), token, authorities);
    }
}
