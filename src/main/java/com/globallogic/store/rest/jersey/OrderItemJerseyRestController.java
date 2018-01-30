package com.globallogic.store.rest.jersey;

import com.globallogic.store.dao.AbstractDAO;
import com.globallogic.store.model.OrderItem;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

/**
 * Jersey rest controller for {@link OrderItem}.
 *
 * @author oleksii.slavik
 */
@Path("/orderItems")
public class OrderItemJerseyRestController {

    /**
     * {@link OrderItem} DAO object for access to database.
     */
    private AbstractDAO<OrderItem> orderItemDao;

    /**
     * Injection of {@link OrderItem} DAO object for access to database.
     */
    public void setOrderItemDao(AbstractDAO<OrderItem> orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    /**
     * Return list of {@link OrderItem} represented as json.
     *
     * @return list of {@link OrderItem}
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderItem> getOrderItemList() {
        return orderItemDao.findByParams(Collections.<String, String>emptyMap());
    }

    /**
     * Return {@link OrderItem} item with given id
     *
     * @param id given id
     * @return {@link OrderItem} item
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public OrderItem getOrderItemById(@PathParam("id") Long id) {
        return orderItemDao.findById(id);
    }

    /**
     * Create given {@link OrderItem}
     *
     * @param orderItem given {@link OrderItem}
     * @return created {@link OrderItem}
     */
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemDao.create(orderItem);
    }

    /**
     * Update {@link OrderItem} item with given id
     *
     * @param id    given id of {@link OrderItem}
     * @param orderItem updated {@link OrderItem} data
     * @return updated {@link OrderItem}
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public OrderItem updateOrderItem(@PathParam("id") Long id, OrderItem orderItem) {
        orderItem.setId(id);
        return orderItemDao.update(orderItem);
    }

    /**
     * Delete {@link OrderItem} item with given id
     *
     * @param id given id of {@link OrderItem}
     * @return deleted {@link OrderItem}
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public OrderItem deleteOrderItem(@PathParam("id") Long id) {
        return orderItemDao.delete(id);
    }
}
