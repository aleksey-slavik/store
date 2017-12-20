package com.globallogic.store.controller.rest;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.model.Order;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private AbstractGenericDAO orderDao;

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getOrderById(@PathVariable Long id) {
        return (Order) orderDao.findById(id);
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getOrderList() {
        return orderDao.findAll();
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Long createOrder(@RequestBody Order order) {
        return orderDao.create(order);
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id);
        orderDao.update(order);
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteOrderById(@PathVariable Long id) {
        orderDao.delete(id);
    }

    public void setOrderDao(AbstractGenericDAO orderDao) {
        this.orderDao = orderDao;
    }
}
