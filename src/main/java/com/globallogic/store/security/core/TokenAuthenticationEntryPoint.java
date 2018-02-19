package com.globallogic.store.security.core;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Rest service entry point
 *
 * @author oleksii.slavik
 */
public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * This is invoked when user tries to access a secured REST resource without supplying any credentials
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
