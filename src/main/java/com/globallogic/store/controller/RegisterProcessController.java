package com.globallogic.store.controller;

import com.globallogic.store.crud.CrudManager;
import com.globallogic.store.exception.SameUserFoundException;
import com.globallogic.store.model.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for register process
 *
 * @author oleksii.slavik
 */
public class RegisterProcessController extends AbstractController {

    /**
     * Name of firstname field in register form
     */
    private static final String FIRSTNAME = "firstname";

    /**
     * Name of lastname field in register form
     */
    private static final String LASTNAME = "lastname";

    /**
     * Name of username field in register form
     */
    private static final String USERNAME = "username";

    /**
     * Name of password field in register form
     */
    private static final String PASSWORD = "password";

    /**
     * Name of confirm password field in register form
     */
    private static final String CONFIRM_PASSWORD = "confirm-password";

    /**
     * Name of email field in register form
     */
    private static final String EMAIL = "email";

    /**
     * Key of message value for register page
     */
    private static final String MESSAGE_KEY = "message";

    /**
     * Key of firstname value for register page
     */
    private static final String FIRSTNAME_KEY = "firstname";

    /**
     * Key of lastname value for register page
     */
    private static final String LASTNAME_KEY = "lastname";

    /**
     * Key of username value for register page
     */
    private static final String USERNAME_KEY = "username";

    /**
     * Key of email value for register page
     */
    private static final String EMAIL_KEY = "email";

    /**
     * Key of user value for register page
     */
    private static final String USER_KEY = "user";

    /**
     * Message for check password
     */
    private static final String MESSAGE_CHECK_PASSWORD = "Check the correct input of password";

    /**
     * Message for already registered users
     */
    private static final String MESSAGE_SAME_USER = "User with given username is already registered";

    /**
     * Name of register page view
     */
    private static final String REGISTER_VIEW = "register";

    /**
     * Name of home page view
     */
    private static final String HOME_VIEW = "redirect:/home";

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

        if (!password.equals(confirmPassword)) {
            Map<String,String> params = new HashMap<String, String>();
            params.put(MESSAGE_KEY, MESSAGE_CHECK_PASSWORD);
            params.put(FIRSTNAME_KEY, firstname);
            params.put(LASTNAME_KEY, lastname);
            params.put(USERNAME_KEY, username);
            params.put(EMAIL_KEY, email);
            return new ModelAndView(REGISTER_VIEW, params);
        }

        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        try {
            Long id = CrudManager.registerUser(user);
            HttpSession session = httpServletRequest.getSession();
            user.setId(id);
            session.setAttribute(USER_KEY, user);
            return new ModelAndView(HOME_VIEW);
        } catch (SameUserFoundException e) {
            Map<String,String> params = new HashMap<String, String>();
            params.put(MESSAGE_KEY, MESSAGE_SAME_USER);
            return new ModelAndView(REGISTER_VIEW, params);
        }
    }
}
