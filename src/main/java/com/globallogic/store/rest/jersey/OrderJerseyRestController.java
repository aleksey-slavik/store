package com.globallogic.store.rest.jersey;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.model.orders.order.Order;

import javax.ws.rs.*;

/**
 * Jersey rest controller for {@link Order}.
 *
 * @author oleksii.slavik
 */
@Path("/orders")
public class OrderJerseyRestController {

    /**
     * Injection of {@link Order} DAO object for access to database.
     */
    private GenericDao<Order> orderDao;

    /**
     * Injection of {@link Order} DAO object for access to database.
     */
    public void setOrderDao(GenericDao<Order> orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Return list of {@link Order} represented as json.
     *
     * @return list of {@link Order}
     */
    /*@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrderList() {
        return orderDao.findByParams(Collections.<String, String>emptyMap());
    }

    *//**
     * Return {@link Order} item with given id
     *
     * @param id given id
     * @return {@link Order} item
     *//*
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrderById(@PathParam("id") Long id) {
        return orderDao.findById(id);
    }

    *//**
     * Create given {@link Order}
     *
     * @param orders given {@link Order}
     * @return created {@link Order}
     *//*
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Order createOrder(Order orders) {
        return orderDao.create(orders);
    }

    *//**
     * Update {@link Order} item with given id
     *
     * @param id    given id of {@link Order}
     * @param orders updated {@link Order} data
     * @return updated {@link Order}
     *//*
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Order updateOrder(@PathParam("id") Long id, Order orders) {
        orders.setId(id);
        return orderDao.update(orders);
    }

    *//**
     * Delete {@link Order} item with given id
     *
     * @param id given id of {@link Order}
     * @return deleted {@link Order}
     *//*
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Order deleteOrder(@PathParam("id") Long id) {
        return orderDao.delete(id);
    }*/
}
