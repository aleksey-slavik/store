package com.globallogic.store.web.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web controller for access to user cart.
 *
 * @author oleksii.slavik
 */
public class CartWebController extends AbstractController {

    @Value("${view.cart}")
    private String cartView;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return new ModelAndView(cartView);
    }
}
