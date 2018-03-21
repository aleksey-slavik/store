package com.globallogic.store.service;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.criteria.SearchCriteria;
import com.globallogic.store.domain.order.Order;
import com.globallogic.store.domain.order.OrderItem;
import com.globallogic.store.domain.order.Status;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.exception.NoContentException;
import com.globallogic.store.exception.NotAcceptableException;
import com.globallogic.store.exception.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class OrderService {

    private GenericDao<Order> orderDao;

    private UserService userService;

    private OrderItemService orderItemService;

    public OrderService(
            GenericDao<Order> orderDao,
            UserService userService,
            OrderItemService orderItemService) {
        this.orderDao = orderDao;
        this.userService = userService;
        this.orderItemService = orderItemService;
    }

    public Order getById(long id) throws NotFoundException {
        Order order = orderDao.getEntityByKey(id);

        if (order == null) {
            throw new NotFoundException("Order with id=" + id + " not found!");
        } else {
            return order;
        }
    }

    public List<Order> getList(SearchCriteria criteria) throws NoContentException {
        List<Order> orders = orderDao.getEntityList(criteria);

        if (orders == null || orders.isEmpty()) {
            throw new NoContentException("Orders with given criteria not found!");
        } else {
            return orders;
        }
    }

    public Order createEmptyOrder() throws NotAcceptableException {
        try {
            Order order = new Order();
            String principal = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getByUsername(principal);
            order.setCustomer(user);
            order.setStatus(Status.OPENED);
            order.setTotalCost(0);
            order.setCreatedDate(System.currentTimeMillis());
            return orderDao.createEntity(order);
        } catch (NotFoundException e) {
            throw new NotAcceptableException("Can't create order!");
        }
    }

    public Order update(long id, Order order) throws NotAcceptableException {
        try {
            getById(id);
            order.setId(id);
            return orderDao.updateEntity(order);
        } catch (NotFoundException e) {
            throw new NotAcceptableException("Can't update order with id=" + id + "!");
        }
    }

    public Order remove(long id) throws NotFoundException {
        Order order = getById(id);
        return orderDao.deleteEntity(order);
    }

    public Order appendItem(long id, OrderItem item) throws NotFoundException {
        Order order = getById(id);
        order.appendItem(item);
        return orderDao.updateEntity(order);
    }

    public Order updateItem(long id, long itemId, OrderItem item) throws NotFoundException {
        Order order = getById(id);
        item.getProduct().setId(itemId);
        order.updateItem(item);
        return orderDao.updateEntity(order);
    }

    public OrderItem deleteItem(long id, long itemId) throws NotFoundException {
        Order order = getById(id);
        OrderItem item = orderItemService.getByIds(id, itemId);
        order.deleteItem(item);
        orderDao.updateEntity(order);
        return item;
    }

    public Order deleteAllItems(long id) throws NotFoundException {
        Order order = getById(id);
        order.clear();
        return orderDao.updateEntity(order);
    }
}
