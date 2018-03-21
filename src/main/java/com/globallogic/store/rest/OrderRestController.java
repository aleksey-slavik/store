package com.globallogic.store.rest;

import com.globallogic.store.converter.order.OrderConverter;
import com.globallogic.store.converter.order.OrderItemConverter;
import com.globallogic.store.dao.criteria.SearchCriteria;
import com.globallogic.store.domain.order.Status;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.dto.order.OrderDTO;
import com.globallogic.store.dto.order.OrderItemDTO;
import com.globallogic.store.domain.order.Order;
import com.globallogic.store.domain.order.OrderItem;
import com.globallogic.store.exception.NoContentException;
import com.globallogic.store.exception.NotAcceptableException;
import com.globallogic.store.exception.NotFoundException;
import com.globallogic.store.service.OrderItemService;
import com.globallogic.store.service.OrderService;
import com.globallogic.store.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
     * order service
     */
    private OrderService orderService;

    /**
     * order item service
     */
    private OrderItemService orderItemService;

    /**
     * user service
     */
    private UserService userService;

    /**
     * order converter
     */
    private OrderConverter orderConverter;

    /**
     * order item converter
     */
    private OrderItemConverter orderItemConverter;

    public OrderRestController(
            OrderService orderService,
            OrderItemService orderItemService,
            UserService userService,
            OrderConverter orderConverter,
            OrderItemConverter orderItemConverter) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.userService = userService;
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
            @ApiParam(value = "order id", required = true) @PathVariable long id) throws NotFoundException {

        Order order = orderService.getById(id);
        return ResponseEntity.ok().body(orderConverter.toResource(order));
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
            @ApiParam(value = "sorting order", defaultValue = "asc") @RequestParam(value = "order", defaultValue = "asc") String order) throws NotFoundException, NoContentException {

        User user = customer != null ? userService.getByUsername(customer) : null;

        SearchCriteria criteria = new SearchCriteria()
                .criteria("customer", user)
                .criteria("status", status)
                .offset(page)
                .limit(size)
                .sortBy(sort)
                .order(order);

        List<Order> orders = orderService.getList(criteria);
        return ResponseEntity.ok().body(orderConverter.toResources(orders));
    }

    /**
     * Resource to create a order
     *
     * @return created order
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to create a order",
            notes = "This can only be done by the authenticated user, which is a customer of order or have admin role")
    public ResponseEntity<?> createOrder() throws NotAcceptableException {

        Order created = orderService.createEmptyOrder();
        return ResponseEntity.ok().body(orderConverter.toResource(created));
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
            @ApiParam(value = "updated order object", required = true) @Valid @RequestBody OrderDTO order) throws NotAcceptableException {

        Order updated = orderService.update(id, order);
        return ResponseEntity.ok().body(orderConverter.toResource(updated));
    }

    /**
     * Resource to delete a order
     *
     * @param id order id
     * @return deleted order
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to delete a order",
            notes = "This can only be done by user, which have admin role")
    public ResponseEntity<?> deleteOrderById(
            @ApiParam(value = "order id", required = true) @PathVariable long id) throws NotFoundException {

        Order deleted = orderService.remove(id);
        return ResponseEntity.ok().body(orderConverter.toResource(deleted));
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
            @ApiParam(value = "order item id", required = true) @PathVariable long itemId) throws NotFoundException {

        OrderItem item = orderItemService.getByIds(id, itemId);
        return ResponseEntity.ok().body(orderItemConverter.toResource(item));
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
            @ApiParam(value = "sorting order", defaultValue = "asc") @RequestParam(value = "order", defaultValue = "asc") String order) throws NoContentException {

        SearchCriteria criteria = new SearchCriteria()
                .criteria("order", id)
                .offset(page)
                .limit(size)
                .sortBy(sort)
                .order(order);

        List<OrderItem> items = orderItemService.getList(criteria);
        return ResponseEntity.ok().body(orderItemConverter.toResources(items));
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
            @ApiParam(value = "order item object", required = true) @Valid @RequestBody OrderItemDTO orderItem) throws NotFoundException {

        orderService.appendItem(id, orderItemConverter.toOrigin(orderItem));
        return ResponseEntity.ok().body(orderItem);
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
            @ApiParam(value = "order item object", required = true) @Valid @RequestBody OrderItemDTO orderItem) throws NotFoundException {

        orderService.updateItem(id, itemId, orderItemConverter.toOrigin(orderItem));
        return ResponseEntity.ok().body(orderItem);
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
            @ApiParam(value = "order item id", required = true) @PathVariable long itemId) throws NotFoundException {

        OrderItem item = orderService.deleteItem(id, itemId);
        return ResponseEntity.ok().body(orderItemConverter.toResource(item));
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
            @ApiParam(value = "order id", required = true) @PathVariable long id) throws NotFoundException {

        Order order = orderService.deleteAllItems(id);
        return ResponseEntity.ok().body(orderConverter.toResource(order));
    }
}
