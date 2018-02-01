package com.globallogic.store.web.user;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web controller for access to user list.
 *
 * @author oleksii.slavik
 */
public class UserWebController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return new ModelAndView("user/users");
    }
}