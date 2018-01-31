package com.globallogic.store.rest.spring;

import com.globallogic.store.dao.AbstractDAO;
import com.globallogic.store.exception.NotFoundException;
import com.globallogic.store.filter.OrderSearchFilter;
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
 * Spring rest controller for {@link Order}.
 *
 * @author oleksii.slavik
 */
@RestController
public class OrderSpringRestController {

    /**
     * {@link Order} DAO object for access to database.
     */
    private AbstractDAO<Order> orderDao;

    /**
     * {@link OrderItem} DAO object for access to database.
     */
    private AbstractDAO<OrderItem> orderItemDao;

    private UserSpringRestController userRest;

    /**
     * Injection of {@link Order} DAO object for access to database.
     */
    public void setOrderDao(AbstractDAO<Order> orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Injection of {@link OrderItem} DAO object for access to database.
     */
    public void setOrderItemDao(AbstractDAO<OrderItem> orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    public void setUserRest(UserSpringRestController userRest) {
        this.userRest = userRest;
    }

    /**
     * Return list of {@link Order} represented as json.
     *
     * @return list of {@link Order}
     */
    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> findOrder(@RequestParam final MultiValueMap<String, String> params) {
        if (params.isEmpty()) {
            return orderDao.findByParams(Collections.<String, String>emptyMap());
        }

        List<Order> orders;

        if (params.containsKey("username") && params.containsKey("status")) {
            OrderSearchFilter orderSearchFilter = new OrderSearchFilter();

            User user = userRest.findUser(new LinkedMultiValueMap<String, String>() {{
                put("username", Collections.singletonList(params.getFirst("username")));
            }}).get(0);

            orderSearchFilter.setUser(user);
            orderSearchFilter.setStatus(Status.valueOf(params.getFirst("status")));

            orders = orderDao.findByFilter(orderSearchFilter);
        } else {
            orders = orderDao.findByParams(params.toSingleValueMap());
        }

        if (orders == null || orders.isEmpty()) {
            throw new NotFoundException();
        } else {
            return orders;
        }
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getOrderById(@PathVariable Long id) {
        return orderDao.findById(id);
    }

    @RequestMapping(value = "/orders/{id}/items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<OrderItem> getItemsOfOrderByOrderId(final @PathVariable Long id) {
        return orderDao.findById(id).getItems();
    }

    @RequestMapping(value = "/orders/{id}/items/{itemId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem getItemOfOrderByItemId(@PathVariable Long id, @PathVariable Long itemId) {
        Set<OrderItem> items = orderDao.findById(id).getItems();

        for (OrderItem item : items) {
            if (item.getId().equals(itemId)) {
                return item;
            }
        }

        throw  new NotFoundException();
    }

    @RequestMapping(value = "/orders/{id}/items", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem addOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        Order order = new Order();
        order.setId(id);
        orderItem.setOrder(order);
        return orderItemDao.create(orderItem);
    }

    @RequestMapping(value = "/orders/{id}/items/{itemId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem updateOrderItem(@PathVariable Long id, @PathVariable Long itemId, @RequestBody OrderItem orderItem) {
        Order order = new Order();
        order.setId(id);
        orderItem.setOrder(order);
        orderItem.setId(itemId);
        return orderItemDao.update(orderItem);
    }

    @RequestMapping(value = "/orders/{id}/items/{itemId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItem deleteOrderItem(@PathVariable Long id, @PathVariable Long itemId) {
        return orderItemDao.delete(itemId);
    }

    /**
     * Create given {@link Order}
     *
     * @param order given {@link Order}
     * @return created {@link Order}
     */
    @RequestMapping(value = "/orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Order createOrder(@RequestBody Order order) {
        return orderDao.create(order);
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
        return orderDao.update(order);
    }

    /**
     * Delete {@link Order} item with given id
     *
     * @param id given id of {@link Order}
     * @return deleted {@link Order}
     */
    @RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Order deleteOrderById(@PathVariable Long id) {
        return orderDao.delete(id);
    }
}
