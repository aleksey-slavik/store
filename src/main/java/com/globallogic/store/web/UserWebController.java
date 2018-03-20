package com.globallogic.store.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserWebController {

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/user",
            method = RequestMethod.GET)
    public ModelAndView getUserList() {
        return new ModelAndView("user/users");
    }

    @PreAuthorize("hasAuthority('CUSTOMER') && !hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/account/products",
            method = RequestMethod.GET)
    public ModelAndView getUserProductList() {
        return new ModelAndView("user/products");
    }

    @PreAuthorize("hasAuthority('CUSTOMER') && !hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/account/orders",
            method = RequestMethod.GET)
    public ModelAndView getUserOrderList() {
        return new ModelAndView("user/orders");
    }

    @PreAuthorize("hasAuthority('CUSTOMER') && !hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/account/shared",
            method = RequestMethod.GET)
    public ModelAndView getSharedProductList() {
        return new ModelAndView("user/shared");
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/account",
            method = RequestMethod.GET)
    public ModelAndView getUserAccount() {
        return new ModelAndView("user/account");
    }

    @PreAuthorize("isAnonymous()")
    @RequestMapping(
            value = "/register",
            method = RequestMethod.GET)
    public ModelAndView getUserRegistration() {
        return new ModelAndView("user/register");
    }

    @PreAuthorize("isAnonymous()")
    @RequestMapping(
            value = "/login",
            method = RequestMethod.GET)
    public ModelAndView getUserLogin(
            @RequestParam(value = "error", required = false) String error) {

        ModelAndView mav = new ModelAndView("user/login");

        if (error != null) {
            mav.addObject("message", "Error during login process! Check input data!");
        }

        return mav;
    }
}
