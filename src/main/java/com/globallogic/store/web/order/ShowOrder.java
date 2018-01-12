package com.globallogic.store.web.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.store.model.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

public class ShowOrder extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String id = httpServletRequest.getParameter("id");

        if (id == null) {
            return getOrderList();
        } else {
            return getOrderById(Long.valueOf(id));
        }
    }

    private ModelAndView getOrderById(Long id) {
        ModelAndView mav = new ModelAndView();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Order order = mapper.readValue(new URL("http://localhost:8080/orders/" + id), Order.class);
            mav.addObject("order", order);
            mav.setViewName("order/item");
        } catch (IOException e) {
            mav.setViewName("error/error");
        }

        return mav;
    }

    private ModelAndView getOrderList() {
        ModelAndView mav = new ModelAndView();

        System.out.println("ORDER LIST: " + SecurityContextHolder.getContext().getAuthentication());

        try {
            ObjectMapper mapper = new ObjectMapper();
            Order[] orders = mapper.readValue(new URL("http://localhost:8080/orders"), Order[].class);
            mav.addObject("orders", orders);
            mav.setViewName("order/list");
        } catch (IOException e) {
            mav.addObject("message", e.getMessage());
            mav.setViewName("error/error");
        }

        return mav;
    }
}
