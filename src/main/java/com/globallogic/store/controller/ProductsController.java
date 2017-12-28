package com.globallogic.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.store.model.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URL;

/**
 * Controller for product list page
 *
 * @author oleksii.slavik
 */
@Controller
public class ProductsController {

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
    public ModelAndView saveProductItem(@ModelAttribute("productForm") Product product) {
        HttpEntity<Product> request = new HttpEntity<Product>(product);
        RestTemplate template = new RestTemplate();
        ResponseEntity<Product> response = template.postForEntity("http://localhost:8080/products/", request, Product.class);
        System.out.println(response.getStatusCode());
        mav.setViewName("redirect:/home");
        return mav;
    }

    @RequestMapping(value = {"/createProduct"}, method = RequestMethod.GET)
    public ModelAndView create() {
        mav.setViewName("createProductItem");
        return mav;
    }

    @RequestMapping(value = {"/updateProduct"}, method = RequestMethod.GET)
    public ModelAndView update(@RequestParam(value = "id") Long id) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Product product = mapper.readValue(new URL("http://localhost:8080/products/" + id), Product.class);
            mav.addObject("product", product);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mav.setViewName("updateProductItem");
        return mav;
    }

    @RequestMapping(value = "/updateProductProcess", method = RequestMethod.POST)
    public ModelAndView updateProductItem(@RequestParam(value = "id") Long id, @ModelAttribute("productForm") Product product) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Product> request = new HttpEntity<Product>(product, headers);
        RestTemplate template = new RestTemplate();
        template.exchange("http://localhost:8080/products/" + id, HttpMethod.PUT, request, Void.class);
        mav.setViewName("redirect:/home");
        return mav;
    }

    @RequestMapping(value = {"/deleteProduct"}, method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam(value = "id") Long id) {
        RestTemplate template = new RestTemplate();
        template.delete("http://localhost:8080/products/" + id);
        mav.setViewName("redirect:/home");
        return mav;
    }
}