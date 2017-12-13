package com.globallogic.store.controller;

import com.globallogic.store.crud.CrudManager;
import com.globallogic.store.model.Login;
import com.globallogic.store.model.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for login process
 *
 * @author oleksii.slavik
 */
public class LoginProcessController extends AbstractController {

    /**
     * Name of username field in register form
     */
    private static final String USERNAME = "username";

    /**
     * Name of password field in register form
     */
    private static final String PASSWORD = "password";

    /**
     * Key of message value for register page
     */
    private static final String MESSAGE_KEY = "message";

    /**
     * Key of user value for register page
     */
    private static final String USER_KEY = "user";

    /**
     * Message for invalid login data
     */
    private static final String MESSAGE_USER_NOT_FOUND = "User with given username and password not found";

    /**
     * Name of login page view
     */
    private static final String LOGIN_VIEW = "login";

    /**
     * Name of home page view
     */
    private static final String HOME_VIEW = "redirect:/home";

    /**
     * Validate input data in login page.
     * Redirect to home page if valid user.
     * Redirect to login page in otherwise.
     *
     * @param httpServletRequest  http request
     * @param httpServletResponse http response
     * @return redirect page
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String username = httpServletRequest.getParameter(USERNAME);
        String password = httpServletRequest.getParameter(PASSWORD);

        Login login = new Login();
        login.setUsername(username);
        login.setPassword(password);

        try {
            User user = CrudManager.verifyUser(login);
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute(USER_KEY, user);
            return new ModelAndView(HOME_VIEW);
        } catch (NoResultException e) {
            Map<String,String> params = new HashMap<String, String>();
            params.put(MESSAGE_KEY, MESSAGE_USER_NOT_FOUND);
            return new ModelAndView(LOGIN_VIEW, params);
        }
    }
}