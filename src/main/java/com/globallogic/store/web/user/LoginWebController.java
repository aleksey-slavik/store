package com.globallogic.store.web.user;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web controller for access login page.
 *
 * @author oleksii.slavik
 */
public class LoginWebController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String error = httpServletRequest.getParameter("error");
        ModelAndView mav = new ModelAndView();

        if (error != null) {
            mav.addObject("message", "Error during login process! Check input data!");
        }

        mav.setViewName("user/login");
        return mav;
    }
}
