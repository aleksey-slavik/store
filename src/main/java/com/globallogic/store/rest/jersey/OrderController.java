package com.globallogic.store.rest.jersey;

import com.globallogic.store.dao.AbstractDAO;
import com.globallogic.store.model.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Jersey rest controller for {@link Order}.
 *
 * @author oleksii.slavik
 */
@Path("/orders")
public class OrderController {

    /**
     * {@link Order} DAO object for access to database.
     */
    private AbstractDAO<Order> orderDao = new AbstractDAO<Order>(Order.class);

    /**
     * Return list of {@link Order} represented as json.
     *
     * @return list of {@link Order}
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrderList() {
        return orderDao.findAll();
    }

    /**
     * Return {@link Order} item with given id
     *
     * @param id given id
     * @return {@link Order} item
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrderById(@PathParam("id") Long id) {
        return orderDao.findById(id);
    }

    /**
     * Create given {@link Order}
     *
     * @param order given {@link Order}
     * @return created {@link Order}
     */
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Order createOrder(Order order) {
        return orderDao.create(order);
    }

    /**
     * Update {@link Order} item with given id
     *
     * @param id    given id of {@link Order}
     * @param order updated {@link Order} data
     * @return updated {@link Order}
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Order updateOrder(@PathParam("id") Long id, Order order) {
        order.setId(id);
        return orderDao.update(order);
    }

    /**
     * Delete {@link Order} item with given id
     *
     * @param id given id of {@link Order}
     * @return deleted {@link Order}
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Order deleteOrder(@PathParam("id") Long id) {
        return orderDao.delete(id);
    }
}
