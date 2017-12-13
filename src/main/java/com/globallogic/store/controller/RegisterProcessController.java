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
            params.put("message", "Check the correct input of password");
            params.put("firstname", firstname);
            params.put("lastname", lastname);
            params.put("username", username);
            params.put("email", email);
            return new ModelAndView("register", params);
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
            session.setAttribute("user", user);
            return new ModelAndView("home");
        } catch (SameUserFoundException e) {
            Map<String,String> params = new HashMap<String, String>();
            params.put("message", "User with given username is already registered");
            return new ModelAndView("register", params);
        }
    }
}
