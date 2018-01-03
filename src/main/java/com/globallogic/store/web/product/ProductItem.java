package com.globallogic.store.web.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.store.model.Product;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

public class ProductItem extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ObjectMapper mapper = new ObjectMapper();
        ModelAndView mav = new ModelAndView();
        Long id = Long.valueOf(httpServletRequest.getParameter("id"));

        try {
            Product product = mapper.readValue(new URL("http://localhost:8080/products/" + id), Product.class);
            mav.addObject("product", product);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mav.setViewName("productItem");
        return mav;
    }
}
