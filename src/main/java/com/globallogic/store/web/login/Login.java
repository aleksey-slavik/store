package com.globallogic.store.web.login;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String error = httpServletRequest.getParameter("error");
        ModelAndView mav = new ModelAndView();

        if (error != null) {
            mav.addObject("message", "Error during login process! Check input data!");
        }

        mav.setViewName("login");
        return mav;
    }
}
