package com.globallogic.store.controller;

import com.globallogic.store.crud.CrudManager;
import com.globallogic.store.model.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for register process
 *
 * @author oleksii.slavik
 */
public class RegisterProcessController extends AbstractController {

    /**
     * Name of username field in register form
     */
    private static final String USERNAME = "username";

    /**
     * Name of password field in register form
     */
    private static final String PASSWORD = "password";

    /**
     * Name of email field in register form
     */
    private static final String EMAIL = "email";

    /**
     * Register user
     *
     * @param httpServletRequest  http request
     * @param httpServletResponse http response
     * @return welcome page
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String firstname = httpServletRequest.getParameter(FIRSTNAME);
        String lastname = httpServletRequest.getParameter(LASTNAME);
        String username = httpServletRequest.getParameter(USERNAME);
        String password = httpServletRequest.getParameter(PASSWORD);
        String confirmPassword = httpServletRequest.getParameter(CONFIRM_PASSWORD);
        String email = httpServletRequest.getParameter(EMAIL);

        CrudManager.registerUser();
    }
}
