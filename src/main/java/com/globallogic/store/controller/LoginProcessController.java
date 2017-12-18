package com.globallogic.store.controller;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.field.Form;
import com.globallogic.store.field.Key;
import com.globallogic.store.field.Param;
import com.globallogic.store.field.View;
import com.globallogic.store.model.Login;
import com.globallogic.store.model.User;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
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
        String username = httpServletRequest.getParameter(Form.Login.USERNAME);
        String password = httpServletRequest.getParameter(Form.Login.PASSWORD);

        Login login = new Login();
        login.setUsername(username);
        login.setPassword(password);

        /*
        try {
            WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
            AbstractGenericDAO<User> userDao = (AbstractGenericDAO<User>) applicationContext.getBean("userDao");
            User user = userDao.find(login);
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute(Key.USER, user);

            switch (user.getRole()) {
                case ADMIN:
                    return new ModelAndView(View.redirect(View.ORDERS));
                default:
                    return new ModelAndView(View.redirect(View.HOME));
            }
        } catch (NoResultException e) {
            Map<String, String> params = new HashMap<String, String>();
            params.put(Key.MESSAGE, Param.USER_NOT_FOUND);
            return new ModelAndView(View.LOGIN, params);
        }*/

        return new ModelAndView(View.redirect(View.HOME)); //todo remove when fix find method
    }
}