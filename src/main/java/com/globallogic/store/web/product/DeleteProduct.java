package com.globallogic.store.web.product;

import com.globallogic.store.web.DeleteTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProduct extends DeleteTemplate {

    @Override
    public String getRestPath() {
        return "http://localhost:8080/products/";
    }

    @Override
    public String getSuccessViewName() {
        return "redirect:/home";
    }
}
