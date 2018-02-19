package com.globallogic.store.web.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web controller for access to product list.
 *
 * @author oleksii.slavik
 */
public class ProductWebController extends AbstractController {

    @Value("${view.product}")
    private String productView;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return new ModelAndView(productView);
    }
}
