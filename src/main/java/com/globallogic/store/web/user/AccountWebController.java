package com.globallogic.store.web.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web controller for access user page.
 *
 * @author oleksii.slavik
 */
public class AccountWebController extends AbstractController {

    @Value("${view.account}")
    private String accountView;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return new ModelAndView(accountView);
    }
}
