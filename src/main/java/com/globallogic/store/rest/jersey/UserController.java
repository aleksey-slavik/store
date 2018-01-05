package com.globallogic.store.rest.jersey;

import com.globallogic.store.dao.AbstractDAO;
import com.globallogic.store.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Jersey rest controller for {@link User}.
 *
 * @author oleksii.slavik
 */
@Path("/users")
public class UserController {

    /**
     * {@link User} DAO object for access to database.
     */
    private AbstractDAO<User> userDao = new AbstractDAO<User>(User.class);

    /**
     * Return list of {@link User} represented as json.
     *
     * @return list of {@link User}
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUserList() {
        return userDao.findAll();
    }

    /**
     * Return {@link User} item with given id
     *
     * @param id given id
     * @return {@link User} item
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserById(@PathParam("id") Long id) {
        return userDao.findById(id);
    }

    /**
     * Return list of {@link User} items result of search by given key.
     *
     * @param key given key
     * @return list of {@link User}
     */
    @GET
    @Path("/search/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> searchUser(@PathParam("key") String key) {
        return userDao.findByKey(key, "firstname", "lastname", "username");
    }

    /**
     * Create given {@link User}
     *
     * @param user given {@link User}
     * @return created {@link User}
     */
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User createUser(User user) {
        return userDao.create(user);
    }

    /**
     * Update {@link User} item with given id
     *
     * @param id      given id of {@link User}
     * @param user updated {@link User} data
     * @return updated {@link User}
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User updateUser(@PathParam("id") Long id, User user) {
        user.setId(id);
        return userDao.update(user);
    }

    /**
     * Delete {@link User} item with given id
     *
     * @param id given id of {@link User}
     * @return deleted {@link User}
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User deleteUser(@PathParam("id") Long id) {
        return userDao.delete(id);
    }
}
