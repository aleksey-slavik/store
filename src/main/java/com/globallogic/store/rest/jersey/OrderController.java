package com.globallogic.store.rest.jersey;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.model.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/orders")
public class OrderController {

    private AbstractGenericDAO<Order> orderDao = new AbstractGenericDAO<Order>(Order.class);

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrderList() {
        return orderDao.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrderById(@PathParam("id") Long id) {
        return orderDao.findById(id);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Order createOrder(Order order) {
        return orderDao.create(order);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Order updateOrder(@PathParam("id") Long id, Order order) {
        order.setId(id);
        return orderDao.update(order);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Order deleteOrder(@PathParam("id") Long id) {
        return orderDao.delete(id);
    }
}
