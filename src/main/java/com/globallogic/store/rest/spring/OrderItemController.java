package com.globallogic.store.rest.spring;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.model.OrderItem;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderItemController {

    private AbstractGenericDAO<OrderItem> orderItemDao;

    @RequestMapping(value = "/orderItems/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem getOrderItemById(@PathVariable Long id) {
        return orderItemDao.findById(id);
    }

    @RequestMapping(value = "/orderItems", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderItem> getOrderItemList() {
        return orderItemDao.findAll();
    }

    @RequestMapping(value = "/orderItems", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem createOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemDao.create(orderItem);
    }

    @RequestMapping(value = "/orderItems/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        orderItem.setId(id);
        return orderItemDao.update(orderItem);
    }

    @RequestMapping(value = "/orderItems/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem deleteOrderItemById(@PathVariable Long id) {
        return orderItemDao.delete(id);
    }

    public void setOrderItemDao(AbstractGenericDAO<OrderItem> orderItemDao) {
        this.orderItemDao = orderItemDao;
    }
}
