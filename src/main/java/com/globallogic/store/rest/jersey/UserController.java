package com.globallogic.store.rest.jersey;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
}
