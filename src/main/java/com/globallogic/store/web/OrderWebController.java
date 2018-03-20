package com.globallogic.store.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderWebController {

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/order",
            method = RequestMethod.GET)
    public ModelAndView getOrderList() {
        return new ModelAndView("order/orders");
    }

    @PreAuthorize("hasAuthority('CUSTOMER') && !hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/cart",
            method = RequestMethod.GET)
    public ModelAndView getUserCart() {
        return new ModelAndView("order/cart");
    }
}
