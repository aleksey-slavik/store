package com.globallogic.store.controller;

import com.globallogic.store.crud.CrudManager;
import com.globallogic.store.model.Login;
import com.globallogic.store.model.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for login process
 *
 * @author oleksii.slavik
 */
public class LoginProcessController extends AbstractController {

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

        User user = CrudManager.verifyUser(login);
    }
}