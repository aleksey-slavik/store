package com.globallogic.store.web.product;

import com.globallogic.store.model.Product;
import com.globallogic.store.web.CreateTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateProduct extends CreateTemplate<Product> {

    @Override
    public String getFormViewName() {
        return "product/create";
    }

    @Override
    public String getSuccessViewName() {
        return "redirect:/home";
    }

    @Override
    public String postRestPath() {
        return "http://localhost:8080/products/";
    }

    @Override
    public Product getItemFromRequest(HttpServletRequest httpServletRequest) {
        return new Product(httpServletRequest);
    }
}