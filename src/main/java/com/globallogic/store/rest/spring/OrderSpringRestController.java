package com.globallogic.store.rest.spring;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.exception.EmptyResponseException;
import com.globallogic.store.exception.NotAcceptableException;
import com.globallogic.store.exception.NotFoundException;
import com.globallogic.store.model.Order;
import com.globallogic.store.model.OrderItem;
import com.globallogic.store.model.Status;
import com.globallogic.store.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.List;

/**
 * Spring rest controller for {@link Order}.
 *
 * @author oleksii.slavik
 */
@RestController
public class OrderSpringRestController {

    /**
     * {@link Order} DAO object for access to database.
     */
    private GenericDao<Order> orderDao;

    /**
     * {@link OrderItem} DAO object for access to database.
     */
    private GenericDao<OrderItem> orderItemDao;

    /**
     * {@link User} DAO object for access to database.
     */
    private GenericDao<User> userDao;

    /**
     * Injection of {@link Order} DAO object for access to database.
     */
    public void setOrderDao(GenericDao<Order> orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Injection of {@link OrderItem} DAO object for access to database.
     */
    public void setOrderItemDao(GenericDao<OrderItem> orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    /**
     * Injection of {@link User} DAO object for access to database.
     */
    public void setUserDao(GenericDao<User> userDao) {
        this.userDao = userDao;
    }

    /**
     * Return list of {@link Order} represented as json.
     *
     * @return list of {@link Order}
     */
    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> findOrder() {
        return orderDao.entityList();
    }

    @RequestMapping(value = "/orders/customers/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Order findOrderByUsername(final @PathVariable String username) {
        final User user = userDao.entityByValue(new HashMap<String, Object>() {{
            put("username", username);
        }});

        try {
            return orderDao.entityByValue(new HashMap<String, Object>() {{
                put("user", user);
                put("status", Status.OPENED);
            }});
        } catch (NoResultException e) {
            return orderDao.createEntity(new Order(user));
        }
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getOrderById(@PathVariable Long id) {
        Order order = orderDao.entityByKey(id);

        if (order != null) {
            return order;
        } else {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "/orders/{id}/items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderItem> getItemsOfOrderByOrderId(final @PathVariable Long id) {
        final Order order = getOrderById(id);
        List<OrderItem> items = orderItemDao.entityListByValue(new HashMap<String, Object>() {{
            put("order", order);
        }});

        if (items == null || items.isEmpty()) {
            throw new EmptyResponseException();
        }

        return items;
    }

    @RequestMapping(value = "/orders/{id}/items/{itemId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem getItemOfOrderByItemId(@PathVariable Long id, @PathVariable Long itemId) {
        Order order = getOrderById(id);
        OrderItem orderItem = orderItemDao.entityByKey(itemId);

        if (orderItem == null) {
            throw new NotFoundException();
        }

        if (orderItem.getOrder().getId().equals(order.getId())) {
            return orderItem;
        } else {
            throw new NotAcceptableException();
        }
    }

    @RequestMapping(value = "/orders/{id}/items", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem addOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        Order order = getOrderById(id);

        if (order == null) {
            throw new NotFoundException();
        }

        orderItem.setOrder(order);
        OrderItem createdOrderItem = orderItemDao.createEntity(orderItem);
        checkOrderTotalCount(id);
        return createdOrderItem;
    }

    @RequestMapping(value = "/orders/{id}/items/{itemId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem updateOrderItem(@PathVariable Long id, @PathVariable Long itemId, @RequestBody OrderItem orderItem) {
        Order order = getOrderById(id);

        if (order == null) {
            throw new NotFoundException();
        }

        orderItem.setOrder(order);
        orderItem.setId(itemId);
        OrderItem updatedOrderItem = orderItemDao.updateEntity(orderItem);
        checkOrderTotalCount(id);
        return updatedOrderItem;
    }

    @RequestMapping(value = "/orders/{id}/items/{itemId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem deleteOrderItem(@PathVariable Long id, @PathVariable Long itemId) {
        getItemOfOrderByItemId(id, itemId);
        OrderItem deletedOrderItem = orderItemDao.deleteEntity(itemId);
        checkOrderTotalCount(id);
        return deletedOrderItem;
    }

    /**
     * Create given {@link Order}
     *
     * @param order given {@link Order}
     * @return created {@link Order}
     */
    @RequestMapping(value = "/orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Order createOrder(@RequestBody Order order) {
        order.checkTotalCost();
        return orderDao.createEntity(order);
    }

    /**
     * Update {@link Order} item with given id
     *
     * @param id    given id of {@link Order}
     * @param order updated {@link Order} data
     * @return updated {@link Order}
     */
    @RequestMapping(value = "/orders/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id);
        order.checkTotalCost();

        for (OrderItem item : order.getItems()) {
            item.setOrder(order);
        }

        return orderDao.updateEntity(order);
    }

    /**
     * Delete {@link Order} item with given id
     *
     * @param id given id of {@link Order}
     * @return deleted {@link Order}
     */
    @RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Order deleteOrderById(@PathVariable Long id) {
        getOrderById(id);
        return orderDao.deleteEntity(id);
    }

    private void checkOrderTotalCount(Long id) {
        Order order = orderDao.entityByKey(id);
        order.checkTotalCost();
        orderDao.updateEntity(order);
    }
}
