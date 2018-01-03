package com.globallogic.store.rest.jersey;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.model.OrderItem;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/orderItems")
public class OrderItemController {

    private AbstractGenericDAO<OrderItem> orderItemDao = new AbstractGenericDAO<OrderItem>(OrderItem.class);

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderItem> getOrderItemList() {
        return orderItemDao.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public OrderItem getOrderItemById(@PathParam("id") Long id) {
        return orderItemDao.findById(id);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemDao.create(orderItem);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public OrderItem updateOrderItem(@PathParam("id") Long id, OrderItem orderItem) {
        orderItem.setId(id);
        return orderItemDao.update(orderItem);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public OrderItem deleteOrderItem(@PathParam("id") Long id) {
        return orderItemDao.delete(id);
    }
}
