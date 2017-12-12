package com.globallogic.store.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for login page
 *
 * @author oleksii.slavik
 */
public class LoginController extends AbstractController {

    /**
     * Name of login page view
     */
    private static final String LOGIN_VIEW = "login";

    /**
     * Redirect to login page
     *
     * @param httpServletRequest  page request
     * @param httpServletResponse page response
     * @return login page
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return new ModelAndView(LOGIN_VIEW);
    }
}
