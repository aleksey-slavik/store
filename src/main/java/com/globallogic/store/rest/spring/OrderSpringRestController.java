package com.globallogic.store.rest.spring;

import com.globallogic.store.dao.AbstractDAO;
import com.globallogic.store.filter.OrderSearchFilter;
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
 * Spring rest controller for {@link Order}.
 *
 * @author oleksii.slavik
 */
@RestController
public class OrderSpringRestController {

    /**
     * Injection of {@link Order} DAO object for access to database.
     */
    private AbstractDAO<Order> orderDao;

    /**
     * Injection of {@link UserSpringRestController} DAO object for access to database.
     */
    private UserSpringRestController userRest;

    /**
     * Injection of {@link Order} DAO object for access to database.
     */
    public void setOrderDao(AbstractDAO<Order> orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Injection of {@link UserSpringRestController} DAO object for access to database.
     */
    public void setUserRest(UserSpringRestController userRest) {
        this.userRest = userRest;
    }

    /**
     * Return list of {@link Order} represented as json.
     *
     * @return list of {@link Order}
     */
    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> findOrder(@RequestParam MultiValueMap<String, String> params) {
        if (params.isEmpty()) {
            return orderDao.findByParams(Collections.<String, String>emptyMap());
        }

        List<Order> orders;

        if (params.containsKey("all")) {
            orders = orderDao.findByParams(Collections.<String, String>emptyMap());
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
    public Set<OrderItem> getItemsOfOrderByOrderId(@PathVariable Long id) {
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

    /**
     * Return list of {@link Order} of given user.
     *
     * @return list of {@link Order}
     */
    //@RequestMapping(value = "/orders/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> findOrderByUsername(@PathVariable final String username, @RequestParam MultiValueMap<String, String> params) {
        List<Order> orders;

        User user = userRest.findUser(new LinkedMultiValueMap<String, String>() {{
            put("username", Collections.singletonList(username));
        }}).get(0);

        OrderSearchFilter orderSearchFilter = new OrderSearchFilter();
        orderSearchFilter.setUser(user);

        if (params.containsKey("status")) {
            orderSearchFilter.setStatus(Status.valueOf(params.getFirst("status")));
        }

        orders = orderDao.findByFilter(orderSearchFilter);

        if (orders == null || orders.isEmpty()) {
            throw new NotFoundException();
        } else {
            return orders;
        }
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
