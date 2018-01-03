package com.globallogic.store.rest.jersey;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
public class UserController {

    private AbstractGenericDAO<User> userDao = new AbstractGenericDAO<User>(User.class);

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUserList() {
        return userDao.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserById(@PathParam("id") Long id) {
        return userDao.findById(id);
    }

    @GET
    @Path("/search/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> searchUser(@PathParam("key") String key) {
        return userDao.findByKey(key, "firstname", "lastname", "username");
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User createUser(User user) {
        return userDao.create(user);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User updateUser(@PathParam("id") Long id, User user) {
        user.setId(id);
        return userDao.update(user);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User deleteUser(@PathParam("id") Long id) {
        return userDao.delete(id);
    }
}
