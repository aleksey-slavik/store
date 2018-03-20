package com.globallogic.store.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Order web controller
 *
 * @author oleksii.slavik
 */
@Controller
public class OrderWebController {

    /**
     * Get list of orders.
     * This can only be done by the authenticated user, which have admin role
     *
     * @return view with list of orders
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/order",
            method = RequestMethod.GET)
    public ModelAndView getOrderList() {
        return new ModelAndView("order/orders");
    }

    /**
     * Get open order for current user
     * This can only be done by the authenticated user, which have only customer role
     *
     * @return view with user cart
     */
    @PreAuthorize("hasAuthority('CUSTOMER') && !hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/cart",
            method = RequestMethod.GET)
    public ModelAndView getUserCart() {
        return new ModelAndView("order/cart");
    }
}
