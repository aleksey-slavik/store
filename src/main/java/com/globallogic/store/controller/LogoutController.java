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
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        HttpSession session = httpServletRequest.getSession();
        if (session != null) {
            session.invalidate();
        }

        return new ModelAndView("home");
    }
}
