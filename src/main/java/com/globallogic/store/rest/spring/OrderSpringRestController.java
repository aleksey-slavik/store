package com.globallogic.store.rest.spring;

import com.globallogic.store.dao.GenericDao;
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
import java.util.Set;

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
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "/orders/customers/{username}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Order createNewOrderForUsername(final @PathVariable String username) {
        final User user = userDao.entityByValue(new HashMap<String, Object>() {{
            put("username", username);
        }});

        return orderDao.createEntity(new Order(user));
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getOrderById(@PathVariable Long id) {
        return orderDao.entityByKey(id);
    }

    @RequestMapping(value = "/orders/{id}/items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<OrderItem> getItemsOfOrderByOrderId(final @PathVariable Long id) {
        return orderDao.entityByKey(id).getItems();
    }

    @RequestMapping(value = "/orders/{id}/items/{itemId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem getItemOfOrderByItemId(@PathVariable Long id, @PathVariable Long itemId) {
        Set<OrderItem> items = orderDao.entityByKey(id).getItems();

        for (OrderItem item : items) {
            if (item.getId().equals(itemId)) {
                return item;
            }
        }

        throw new NotFoundException();
    }

    @RequestMapping(value = "/orders/{id}/items", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem addOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        orderItem.setOrder(new Order(id));
        OrderItem createdOrderItem = orderItemDao.createEntity(orderItem);
        checkOrderTotalCount(id);
        return createdOrderItem;
    }

    @RequestMapping(value = "/orders/{id}/items/{itemId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem updateOrderItem(@PathVariable Long id, @PathVariable Long itemId, @RequestBody OrderItem orderItem) {
        orderItem.setOrder(new Order(id));
        orderItem.setId(itemId);
        OrderItem updatedOrderItem = orderItemDao.updateEntity(orderItem);
        checkOrderTotalCount(id);
        return updatedOrderItem;
    }

    @RequestMapping(value = "/orders/{id}/items/{itemId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem deleteOrderItem(@PathVariable Long id, @PathVariable Long itemId) {
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
        return orderDao.deleteEntity(id);
    }

    private void checkOrderTotalCount(Long id) {
        Order order = orderDao.entityByKey(id);
        order.checkTotalCost();
        orderDao.updateEntity(order);
    }
}
