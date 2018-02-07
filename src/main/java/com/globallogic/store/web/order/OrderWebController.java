package com.globallogic.store.web.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web controller for access to order list.
 *
 * @author oleksii.slavik
 */
public class OrderWebController extends AbstractController {

    @Value("${view.order}")
    private String orderView;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return new ModelAndView(orderView);
    }
}
