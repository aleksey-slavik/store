package com.globallogic.store.web.product;

import com.globallogic.store.model.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateProductProcess extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String name = httpServletRequest.getParameter("name");
        String brand = httpServletRequest.getParameter("brand");
        String description = httpServletRequest.getParameter("description");
        Double price = Double.valueOf(httpServletRequest.getParameter("price"));
        Product product = new Product(name, brand, description, price);

        ModelAndView mav = new ModelAndView();
        HttpEntity<Product> request = new HttpEntity<Product>(product);
        RestTemplate template = new RestTemplate();
        ResponseEntity<Product> response = template.postForEntity("http://localhost:8080/products/", request, Product.class);
        mav.setViewName("redirect:/home");
        return mav;
    }
}
