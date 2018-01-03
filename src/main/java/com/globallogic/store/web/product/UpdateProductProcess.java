package com.globallogic.store.web.product;

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

public class UpdateProductProcess extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Long id = Long.valueOf(httpServletRequest.getParameter("id"));
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
