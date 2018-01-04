package com.globallogic.store.web.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.store.model.Product;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

public class ShowProduct extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String id = httpServletRequest.getParameter("id");

        if (id == null) {
            return getProductList();
        } else {
            return getProductById(Long.valueOf(id));
        }
    }

    private ModelAndView getProductById(Long id) {
        ModelAndView mav = new ModelAndView();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Product product = mapper.readValue(new URL("http://localhost:8080/products/" + id), Product.class);
            mav.addObject("product", product);
            mav.setViewName("product/item");
        } catch (IOException e) {
            mav.setViewName("error/error");
        }

        return mav;
    }

    private ModelAndView getProductList() {
        ModelAndView mav = new ModelAndView();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Product[] products = mapper.readValue(new URL("http://localhost:8080/products"), Product[].class);
            mav.addObject("products", products);
            mav.setViewName("product/list");
        } catch (IOException e) {
            mav.setViewName("error/error");
        }


        return mav;
    }
}
