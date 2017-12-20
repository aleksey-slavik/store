package com.globallogic.store.controller.rest;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.model.OrderItem;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderItemController {
    private AbstractGenericDAO orderItemDao;

    @RequestMapping(value = "/order-items/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem getOrderItemById(@PathVariable Long id) {
        return (OrderItem) orderItemDao.findById(id);
    }

    @RequestMapping(value = "/order-items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderItem> getOrderItemList() {
        return orderItemDao.findAll();
    }

    @RequestMapping(value = "/order-items", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Long createOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemDao.create(orderItem);
    }

    @RequestMapping(value = "/order-items/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        orderItem.setId(id);
        orderItemDao.update(orderItem);
    }

    @RequestMapping(value = "/order-items/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteOrderItemById(@PathVariable Long id) {
        orderItemDao.delete(id);
    }

    public void setOrderItemDao(AbstractGenericDAO orderItemDao) {
        this.orderItemDao = orderItemDao;
    }
}
