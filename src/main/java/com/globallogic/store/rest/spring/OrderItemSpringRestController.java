package com.globallogic.store.rest.spring;

import com.globallogic.store.dao.AbstractDAO;
import com.globallogic.store.exception.EmptyResponseException;
import com.globallogic.store.exception.NotFoundException;
import com.globallogic.store.model.Order;
import com.globallogic.store.model.OrderItem;
import com.globallogic.store.model.Status;
import com.globallogic.store.model.User;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Spring rest controller for {@link OrderItem}.
 *
 * @author oleksii.slavik
 */
@RestController
public class OrderItemSpringRestController {

    /**
     * {@link OrderItem} DAO object for access to database.
     */
    private AbstractDAO<OrderItem> orderItemDao;

    /**
     * {@link OrderSpringRestController} DAO object for access to database.
     */
    private OrderSpringRestController orderRest;

    /**
     * {@link UserSpringRestController} DAO object for access to database.
     */
    private UserSpringRestController userRest;

    /**
     * Injection of {@link OrderSpringRestController} DAO object for access to database.
     */
    public void setOrderItemDao(AbstractDAO<OrderItem> orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    /**
     * Injection of {@link UserSpringRestController} DAO object for access to database.
     */
    public void setOrderRest(OrderSpringRestController orderRest) {
        this.orderRest = orderRest;
    }

    /**
     * Injection of {@link UserSpringRestController} DAO object for access to database.
     */
    public void setUserRest(UserSpringRestController userRest) {
        this.userRest = userRest;
    }

    /**
     * Return {@link OrderItem} item with given id
     *
     * @param id given id
     * @return {@link OrderItem} item
     */
    @RequestMapping(value = "/orderItems/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem getOrderItemById(@PathVariable Long id) {
        return orderItemDao.findById(id);
    }

    /**
     * Return list of {@link OrderItem} represented as json.
     *
     * @return list of {@link OrderItem}
     */
    @RequestMapping(value = "/orderItems", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderItem> findOrderItem(@RequestParam MultiValueMap<String, String> params) {
        if (params.isEmpty()) {
            throw new EmptyResponseException();
        }

        List<OrderItem> orderItems;

        if (params.containsKey("all")) {
            orderItems = orderItemDao.findByParams(Collections.<String, String>emptyMap());
        } else {
            orderItems = orderItemDao.findByParams(params.toSingleValueMap());
        }

        if (orderItems == null || orderItems.isEmpty()) {
            throw new NotFoundException();
        } else {
            return orderItems;
        }
    }

    /**
     * Create given {@link OrderItem}
     *
     * @param orderItem given {@link OrderItem}
     * @return created {@link OrderItem}
     */
    @RequestMapping(value = "/orderItems/{username}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem createOrderItem(@PathVariable final String username, @RequestBody OrderItem orderItem) {
        OrderItem item = orderItemDao.create(orderItem);
        Order order = null;

        try {
            System.out.println("START TRY");

            order = orderRest.findOrderByUsername(username, new LinkedMultiValueMap<String, String>() {{
                put("status", Collections.singletonList(Status.OPENED.name()));
            }}).get(0);

            System.out.println("ORDER EXIST: " + order);
        } catch (NotFoundException e) {
            System.out.println("START CATCH");

            User user = userRest.findUser(new LinkedMultiValueMap<String, String>() {{
                put("username", Collections.singletonList(username));
            }}).get(0);

            order = new Order(user);
            System.out.println("ORDER NEW: " + order);
        } finally {
            assert order != null;
            Set<OrderItem> items = order.getItems();
            items.add(item);
            order.setItems(items);
            orderRest.updateOrder(order.getId(), order);
        }

        return item;
    }

    /**
     * Update {@link OrderItem} item with given id
     *
     * @param id        given id of {@link OrderItem}
     * @param orderItem updated {@link OrderItem} data
     * @return updated {@link OrderItem}
     */
    @RequestMapping(value = "/orderItems/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        orderItem.setId(id);
        return orderItemDao.update(orderItem);
    }

    /**
     * Delete {@link OrderItem} item with given id
     *
     * @param id given id of {@link OrderItem}
     * @return deleted {@link OrderItem}
     */
    @RequestMapping(value = "/orderItems/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem deleteOrderItemById(@PathVariable Long id) {
        return orderItemDao.delete(id);
    }
}
