package com.globallogic.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.store.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.List;

@Controller
public class HttpController {

    private ModelAndView mav = new ModelAndView();

    @RequestMapping(value = {"/", "/home", "/productList"}, method = RequestMethod.GET)
    public ModelAndView home() {

        ObjectMapper mapper = new ObjectMapper();
        try {
            Product[] products = mapper.readValue(new URL("http://localhost:8080/products"), Product[].class);
            mav.addObject("products", products);
        }catch (IOException e) {
            e.printStackTrace();
        }

        mav.setViewName("home");
        return mav;
    }

    @RequestMapping(value = "/productItem", method = RequestMethod.GET)
    public ModelAndView productItem(@PathVariable Long id) {
        mav.setViewName("productItem");
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            mav.addObject("error", "Error during login process! Check input data!");
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
