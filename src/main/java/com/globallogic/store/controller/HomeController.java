package com.globallogic.store.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for home page
 *
 * @author oleksii.slavik
 */
public class HomeController extends AbstractController {

    /**
     * Name of home page view
     */
    private static final String HOME_VIEW = "home";

    /**
     * Redirect to home page
     *
     * @param httpServletRequest  page request
     * @param httpServletResponse page response
     * @return home page
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return new ModelAndView(HOME_VIEW);
    }
}
