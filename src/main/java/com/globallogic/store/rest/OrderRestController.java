package com.globallogic.store.rest;

import com.globallogic.store.converter.order.OrderConverter;
import com.globallogic.store.converter.order.OrderItemConverter;
import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.SearchCriteria;
import com.globallogic.store.domain.order.Status;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.dto.order.OrderDTO;
import com.globallogic.store.dto.order.OrderItemDTO;
import com.globallogic.store.domain.order.Order;
import com.globallogic.store.domain.order.OrderItem;
import com.globallogic.store.security.AuthenticatedUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.validation.Valid;
import java.util.List;

/**
 * Rest controller for operations with orders
 *
 * @author oleksii.slavik
 */
@RestController
@RequestMapping(value = "/api/orders")
@Api(value = "/api/orders", description = "Operations with orders")
public class OrderRestController {

    /**
     * order DAO
     */
    private GenericDao<Order> orderDao;

    /**
     * order item DAO
     */
    private GenericDao<OrderItem> orderItemDao;

    /**
     * user DAO
     */
    private GenericDao<User> userDao;

    /**
     * order converter
     */
    private OrderConverter orderConverter;

    /**
     * order item converter
     */
    private OrderItemConverter orderItemConverter;

    public OrderRestController(
            GenericDao<Order> orderDao,
            GenericDao<OrderItem> orderItemDao,
            GenericDao<User> userDao,
            OrderConverter orderConverter,
            OrderItemConverter orderItemConverter) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.userDao = userDao;
        this.orderConverter = orderConverter;
        this.orderItemConverter = orderItemConverter;
    }

    /**
     * Resource to get a order by id
     *
     * @param id order id
     * @return order with given id
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to get a order by id",
            notes = "This can only be done by the authenticated user, which is a customer of order or have admin role")
    public ResponseEntity<?> getOrderById(
            @ApiParam(value = "order id", required = true) @PathVariable long id) {
        Order order = orderDao.getEntityByKey(id);

        if (order == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok().body(orderConverter.toResource(order));
        }
    }

    /**
     * Resource to get a list of orders
     *
     * @param customer username of customer of order
     * @param status   status of order
     * @param page     page number
     * @param size     count of items per page
     * @param sort     name of sorting column
     * @param order    sorting order
     * @return list of orders
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to get a list of orders",
            notes = "This can only be done by the authenticated user, which is a customer of order or have admin role")
    public ResponseEntity<?> getOrderList(
            @ApiParam(value = "username of customer of order") @RequestParam(value = "customer", required = false) String customer,
            @ApiParam(value = "status of order") @RequestParam(value = "status", required = false) Status status,
            @ApiParam(value = "page number", defaultValue = "1") @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "count of items per page", defaultValue = "5") @RequestParam(value = "size", defaultValue = "5") int size,
            @ApiParam(value = "name of sorting column", defaultValue = "id") @RequestParam(value = "sort", defaultValue = "id") String sort,
            @ApiParam(value = "sorting order", defaultValue = "asc") @RequestParam(value = "order", defaultValue = "asc") String order) {

        User user = null;

        if (customer != null) {
            SearchCriteria customerCriteria = new SearchCriteria().criteria("username", customer);
            user = userDao.getEntity(customerCriteria);
        }

        SearchCriteria criteria = new SearchCriteria()
                .criteria("customer", user)
                .criteria("status", status)
                .offset(page)
                .limit(size)
                .sortBy(sort)
                .order(order);

        List<Order> orders = orderDao.getEntityList(criteria);

        if (orders == null || orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok().body(orderConverter.toResources(orders));
        }
    }

    /**
     * Resource to create a order
     *
     * @param order created order object
     * @return created order
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to create a order",
            notes = "This can only be done by the authenticated user, which is a customer of order or have admin role")
    public ResponseEntity<?> createOrder(
            @ApiParam(value = "created order object") @RequestBody OrderDTO order) {

        if (order == null) {
            order = new OrderDTO();
            AuthenticatedUser principal = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getDetails();
            order.setCustomerId(principal.getId());
            order.setCustomer(principal.getUsername());
            order.setStatus(Status.OPENED);
            order.setTotalCost(0);
        }

        try {
            Order created = orderDao.createEntity(orderConverter.toOrigin(order));
            return ResponseEntity.ok().body(created);
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Resource to update a order
     *
     * @param id    order id
     * @param order updated order object
     * @return updated order
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to update a order",
            notes = "This can only be done by the authenticated user, which is a customer of order or have admin role")
    public ResponseEntity<?> updateOrder(
            @ApiParam(value = "order id", required = true) @PathVariable long id,
            @ApiParam(value = "updated order object", required = true) @Valid @RequestBody OrderDTO order) {

        try {
            order.setId(id);
            Order updated = orderDao.updateEntity(orderConverter.toOrigin(order));
            return ResponseEntity.ok().body(orderConverter.toResource(updated));
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Resource to delete a order
     *
     * @param id order id
     * @return deleted order
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to delete a order",
            notes = "This can only be done by user, which have admin role")
    public ResponseEntity<?> deleteOrderById(
            @ApiParam(value = "order id", required = true) @PathVariable long id) {

        try {
            Order deleted = orderDao.deleteEntityByKey(id);
            return ResponseEntity.ok().body(orderConverter.toResource(deleted));
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Resource to get a order item by id
     *
     * @param id     order id
     * @param itemId order item id
     * @return order item with given id
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/{id}/items/{itemId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to get a order item by id",
            notes = "This can only be done by the authenticated user, which is a customer of order or have admin role")
    public ResponseEntity<?> getOrderItemByItemId(
            @ApiParam(value = "order id", required = true) @PathVariable long id,
            @ApiParam(value = "order item id", required = true) @PathVariable long itemId) {

        try {
            SearchCriteria criteria = new SearchCriteria()
                    .criteria("order", id)
                    .criteria("product", itemId);

            OrderItem item = orderItemDao.getEntity(criteria);
            return ResponseEntity.ok().body(orderItemConverter.toResource(item));
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    /**
     * Resource to get a list of order items
     *
     * @param id    order id
     * @param page  page number
     * @param size  count of items per page
     * @param sort  name of sorting column
     * @param order sorting order
     * @return list of order items
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/{id}/items",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to get a list of order items",
            notes = "This can only be done by the authenticated user, which is a customer of order or have admin role")
    public ResponseEntity<?> getOrderItemsByOrderId(
            @ApiParam(value = "order id", required = true) @PathVariable long id,
            @ApiParam(value = "page number", defaultValue = "1") @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "count of items per page", defaultValue = "5") @RequestParam(value = "size", defaultValue = "5") int size,
            @ApiParam(value = "name of sorting column", defaultValue = "product") @RequestParam(value = "sort", defaultValue = "product") String sort,
            @ApiParam(value = "sorting order", defaultValue = "asc") @RequestParam(value = "order", defaultValue = "asc") String order) {

        SearchCriteria criteria = new SearchCriteria()
                .criteria("order", id)
                .offset(page)
                .limit(size)
                .sortBy(sort)
                .order(order);

        List<OrderItem> items = orderItemDao.getEntityList(criteria);

        if (items == null || items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok().body(orderItemConverter.toResources(items));
        }
    }

    /**
     * Resource to append order item to order list
     *
     * @param id        order id
     * @param orderItem order item object
     * @return appended order item
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/{id}/items",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to append order item to order list",
            notes = "This can only be done by the authenticated user, which is a customer of order or have admin role")
    public ResponseEntity<?> addOrderItem(
            @ApiParam(value = "order id", required = true) @PathVariable long id,
            @ApiParam(value = "order item object", required = true) @Valid @RequestBody OrderItemDTO orderItem) {

        try {
            Order order = orderDao.getEntityByKey(id);
            order.appendItem(orderItemConverter.toOrigin(orderItem));
            orderDao.updateEntity(order);
            return ResponseEntity.ok().body(orderItem);
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Resource to update order item in order list
     *
     * @param id        order id
     * @param itemId    order item id
     * @param orderItem order item object
     * @return updated order item
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/{id}/items/{itemId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to update order item in order list",
            notes = "This can only be done by the authenticated user, which is a customer of order or have admin role")
    public ResponseEntity<?> updateOrderItem(
            @ApiParam(value = "order id", required = true) @PathVariable long id,
            @ApiParam(value = "order item id", required = true) @PathVariable long itemId,
            @ApiParam(value = "order item object", required = true) @Valid @RequestBody OrderItemDTO orderItem) {

        try {
            Order order = orderDao.getEntityByKey(id);
            orderItem.setProductId(itemId);
            order.updateItem(orderItemConverter.toOrigin(orderItem));
            orderDao.updateEntity(order);
            return ResponseEntity.ok().body(orderItem);
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Resource to delete order item from order list
     *
     * @param id     order id
     * @param itemId order item id
     * @return deleted order item
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/{id}/items/{itemId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to delete order item from order list",
            notes = "This can only be done by the authenticated user, which is a customer of order or have admin role")
    public ResponseEntity<?> deleteOrderItem(
            @ApiParam(value = "order id", required = true) @PathVariable long id,
            @ApiParam(value = "order item id", required = true) @PathVariable long itemId) {

        try {
            Order order = orderDao.getEntityByKey(id);

            SearchCriteria criteria = new SearchCriteria()
                    .criteria("order", id)
                    .criteria("product", itemId);

            OrderItem item = orderItemDao.getEntity(criteria);
            order.deleteItem(item);
            orderDao.updateEntity(order);
            return ResponseEntity.ok().body(orderItemConverter.toResource(item));
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Resource to delete all order items from order list
     *
     * @param id order id
     * @return cleared order
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/{id}/items",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to delete all order items from order list",
            notes = "This can only be done by the authenticated user, which is a customer of order or have admin role")
    public ResponseEntity<?> deleteAllOrderItem(
            @ApiParam(value = "order id", required = true) @PathVariable long id) {

        try {
            Order order = orderDao.getEntityByKey(id);
            order.clear();
            orderDao.updateEntity(order);
            return ResponseEntity.ok().body(orderConverter.toResource(order));
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
