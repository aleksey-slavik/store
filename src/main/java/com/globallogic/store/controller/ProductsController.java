package com.globallogic.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.store.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

/**
 * Controller for product list page
 *
 * @author oleksii.slavik
 */
@Controller
public class ProductsController{

    private ModelAndView mav = new ModelAndView();

    @RequestMapping(value = {"/", "/home", "/productList"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Product[] products = mapper.readValue(new URL("http://localhost:8080/products"), Product[].class);
            mav.addObject("products", products);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mav.setViewName("home");
        return mav;
    }

    @RequestMapping(value = "/productItem", method = RequestMethod.GET)
    public ModelAndView showProductItem(@RequestParam(value = "id") Long id) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Product product = mapper.readValue(new URL("http://localhost:8080/products/" + id), Product.class);
            mav.addObject("product", product);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mav.setViewName("productItem");
        return mav;
    }

    @RequestMapping(value = "/createProductProcess", method = RequestMethod.POST)
    public ModelAndView saveProductItem(@ModelAttribute("productForm") Product product, HttpServletRequest request, HttpServletResponse response) {
        /*try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("http://localhost:8080/products/");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        mav.setViewName("home");
        return mav;
    }

    @RequestMapping(value = {"/createProduct"}, method = RequestMethod.GET)
    public ModelAndView create() {
        mav.setViewName("createProductItem");
        return mav;
    }
}
