package com.globallogic.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    private ModelAndView mav = new ModelAndView();

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            mav.addObject("message", "Error during login process! Check input data!");
        }

        mav.setViewName("login");
        return mav;
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied() {
        mav.setViewName("403");
        return mav;
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public ModelAndView register() {
        mav.setViewName("register");
        return mav;
    }
}
