package com.globallogic.store.web.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.store.model.Product;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

public class ProductList extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ObjectMapper mapper = new ObjectMapper();
        ModelAndView mav = new ModelAndView();

        try {
            Product[] products = mapper.readValue(new URL("http://localhost:8080/products"), Product[].class);
            mav.addObject("products", products);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mav.setViewName("home");
        return mav;
    }
}
