package com.globallogic.store.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductWebController {

    @RequestMapping(
            value = {"/", "/home", "/product"},
            method = RequestMethod.GET)
    public ModelAndView getProductList() {
        return new ModelAndView("product/products");
    }
}
