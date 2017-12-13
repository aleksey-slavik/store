package com.globallogic.store.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller for logout process
 *
 * @author oleksii.slavik
 */
public class LogoutController extends AbstractController {

    /**
     * Name of home page view
     */
    private static final String HOME_VIEW = "redirect:/home";

    /**
     * Logout controller
     *
     * @param httpServletRequest  http request
     * @param httpServletResponse http response
     * @return home page
     */
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();

        if (session != null) {
            session.invalidate();
        }

        return new ModelAndView(HOME_VIEW);
    }
}
