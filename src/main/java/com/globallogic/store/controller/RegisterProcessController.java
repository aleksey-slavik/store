package com.globallogic.store.controller;

import com.globallogic.store.dao.UserDAO;
import com.globallogic.store.field.Form;
import com.globallogic.store.field.Key;
import com.globallogic.store.field.Param;
import com.globallogic.store.field.View;
import com.globallogic.store.model.User;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;
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
     * Register user
     *
     * @param httpServletRequest  http request
     * @param httpServletResponse http response
     * @return welcome page
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String firstname = httpServletRequest.getParameter(Form.Register.FIRSTNAME);
        String lastname = httpServletRequest.getParameter(Form.Register.LASTNAME);
        String username = httpServletRequest.getParameter(Form.Register.USERNAME);
        String password = httpServletRequest.getParameter(Form.Register.PASSWORD);
        String confirmPassword = httpServletRequest.getParameter(Form.Register.CONFIRM_PASSWORD);
        String email = httpServletRequest.getParameter(Form.Register.EMAIL);

        if (!password.equals(confirmPassword)) {
            Map<String, String> params = createModelAndViewParams(Param.CHECK_PASSWORD, firstname, lastname, username, email);
            return new ModelAndView(View.REGISTER, params);
        }

        User user = new User(firstname, lastname, username, password, email);
        UserDAO userDAO = new UserDAO();
        Long id = userDAO.create(user);

        if (id != null) {
            HttpSession session = httpServletRequest.getSession();
            user.setId(id);
            session.setAttribute(Key.USER, user);
            return new ModelAndView(View.redirect(View.HOME));
        } else {
            Map<String, String> params = createModelAndViewParams(Param.SAME_USER, firstname, lastname, username, email);
            return new ModelAndView(View.REGISTER, params);
        }
    }

    private Map<String, String> createModelAndViewParams(String message, String firstname, String lastname, String username, String email) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Key.MESSAGE, message);
        params.put(Key.FIRSTNAME, firstname);
        params.put(Key.LASTNAME, lastname);
        params.put(Key.USERNAME, username);
        params.put(Key.EMAIL, email);
        return params;
    }
}
