package com.globallogic.store.security.core;

import com.globallogic.store.security.AuthenticatedUser;
import com.globallogic.store.security.jwt.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Value("${jwt.header}")
    private String header;

    private TokenUtil tokenUtil;

    public TokenAuthenticationFilter() {
        super("/**");
        //authentication is success
        setAuthenticationSuccessHandler(((httpServletRequest, httpServletResponse, authentication) -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            httpServletRequest
                    .getRequestDispatcher(httpServletRequest.getServletPath() + httpServletRequest.getPathInfo())
                    .forward(httpServletRequest, httpServletResponse);
        }));
        //authentication is failure
        setAuthenticationFailureHandler(((httpServletRequest, httpServletResponse, e) ->
                httpServletResponse.getOutputStream().print(e.getMessage())));
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String requestHeader = httpServletRequest.getHeader(this.header);
        UsernamePasswordAuthenticationToken authentication;

        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            String authToken = requestHeader.substring(7);
            AuthenticatedUser user = tokenUtil.parseToken(authToken);
            authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            authentication = new UsernamePasswordAuthenticationToken(null, null);
            authentication.setAuthenticated(false);
        }

        return authentication;
    }

    public void setTokenUtil(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }
}
