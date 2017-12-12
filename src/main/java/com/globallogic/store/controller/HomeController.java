package com.globallogic.store.controller;

import com.globallogic.store.crud.CrudManager;
import com.globallogic.store.model.Login;
import com.globallogic.store.model.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController extends AbstractController {

    private static final String HOME_VIEW = "home";

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Login login = new Login();
        login.setUsername("admin");
        login.setPassword("admin");
        System.out.println(CrudManager.verifyUser(login));
        return new ModelAndView(HOME_VIEW);
    }
}
