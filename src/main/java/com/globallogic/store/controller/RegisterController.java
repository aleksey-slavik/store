package com.globallogic.store.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for register page
 *
 * @author oleksii.slavik
 */
public class RegisterController extends AbstractController {

    /**
     * Name of register page view
     */
    private static final String REGISTER_VIEW = "register";


    /**
     * Redirect to register page
     *
     * @param httpServletRequest  http request
     * @param httpServletResponse http response
     * @return register page
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return new ModelAndView(REGISTER_VIEW);
    }
}