package com.globallogic.store.web.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.store.model.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

public class UpdateProduct extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Long id = Long.valueOf(httpServletRequest.getParameter("id"));
        String method = httpServletRequest.getMethod();

        if (method.equals(METHOD_GET)) {
            return getProductItem(id);
        } else if (method.equals(METHOD_POST)) {
            return updateProductItem(id, httpServletRequest);
        } else {
            return new ModelAndView("error/error");
        }
    }

    private ModelAndView getProductItem(Long id) {
        ModelAndView mav = new ModelAndView();

        ObjectMapper mapper = new ObjectMapper();

        try {
            Product product = mapper.readValue(new URL("http://localhost:8080/products/" + id), Product.class);
            mav.addObject("product", product);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mav.setViewName("product/update");
        return mav;
    }

    private ModelAndView updateProductItem(Long id, HttpServletRequest httpServletRequest) {
        String name = httpServletRequest.getParameter("name");
        String brand = httpServletRequest.getParameter("brand");
        String description = httpServletRequest.getParameter("description");
        Double price = Double.valueOf(httpServletRequest.getParameter("price"));
        Product product = new Product(name, brand, description, price);

        ModelAndView mav = new ModelAndView();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Product> request = new HttpEntity<Product>(product, headers);
        RestTemplate template = new RestTemplate();
        template.exchange("http://localhost:8080/products/" + id, HttpMethod.PUT, request, Void.class);
        mav.setViewName("redirect:/home");
        return mav;
    }
}
