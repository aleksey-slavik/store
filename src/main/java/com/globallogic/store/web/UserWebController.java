package com.globallogic.store.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * User web controller
 *
 * @author oleksii.slavik
 */
@Controller
public class UserWebController {

    /**
     * Get list of users
     * This can only be done by the authenticated user, which have admin role
     *
     * @return view with list of users
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/user",
            method = RequestMethod.GET)
    public ModelAndView getUserList() {
        return new ModelAndView("user/users");
    }

    /**
     * Get list of products, owner of which is current user
     * This can only be done by the authenticated user, which have only customer role
     *
     * @return view with list of product of user
     */
    @PreAuthorize("hasAuthority('CUSTOMER') && !hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/account/products",
            method = RequestMethod.GET)
    public ModelAndView getUserProductList() {
        return new ModelAndView("user/products");
    }

    /**
     * Get list of order of current user
     * This can only be done by the authenticated user, which have only customer role
     *
     * @return view with list of orders of user
     */
    @PreAuthorize("hasAuthority('CUSTOMER') && !hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/account/orders",
            method = RequestMethod.GET)
    public ModelAndView getUserOrderList() {
        return new ModelAndView("user/orders");
    }

    /**
     * Get list of products, to which current user have permissions
     * This can only be done by the authenticated user, which have only customer role
     *
     * @return view with list of products with permission for current user
     */
    @PreAuthorize("hasAuthority('CUSTOMER') && !hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/account/shared",
            method = RequestMethod.GET)
    public ModelAndView getSharedProductList() {
        return new ModelAndView("user/shared");
    }

    /**
     * Get current user account info
     * This can only be done by the authenticated user
     *
     * @return view with user account info
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/account",
            method = RequestMethod.GET)
    public ModelAndView getUserAccount() {
        return new ModelAndView("user/account");
    }

    /**
     * Access denied error page
     *
     * @return view with custom access denied page
     */
    @RequestMapping(
            value = "/403",
            method = RequestMethod.GET)
    public ModelAndView handleAccessDenied() {
        return new ModelAndView("error/403");
    }

    /**
     * Get user registration page
     * This can only be done by the anonymous user
     *
     * @return view with registration form
     */
    @PreAuthorize("isAnonymous()")
    @RequestMapping(
            value = "/register",
            method = RequestMethod.GET)
    public ModelAndView getUserRegistration() {
        return new ModelAndView("user/register");
    }

    /**
     * Get user login page
     * This can only be done by the anonymous user
     *
     * @param error thrown authentication error
     * @return view with login form
     */
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
