package com.globallogic.store.web.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web controller for access denied page.
 * Call when access to some resource is denied.
 *
 * @author oleksii.slavik
 */
public class AccessDeniedWebController extends AbstractController{

    @Value("${view.forbidden}")
    private String forbiddenView;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return new ModelAndView(forbiddenView);
    }
}
