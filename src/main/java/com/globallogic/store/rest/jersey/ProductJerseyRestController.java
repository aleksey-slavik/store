package com.globallogic.store.rest.jersey;

import com.globallogic.store.dao.AbstractDAO;
import com.globallogic.store.model.Product;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

/**
 * Jersey rest controller for {@link Product}.
 *
 * @author oleksii.slavik
 */
@Path("/products")
public class ProductJerseyRestController {

    /**
     * {@link Product} DAO object for access to database.
     */
    private AbstractDAO<Product> productDao;

    /**
     * Injection {@link Product} DAO object for access to database.
     */
    public void setProductDao(AbstractDAO<Product> productDao) {
        this.productDao = productDao;
    }

    /**
     * Return list of {@link Product} represented as json.
     *
     * @return list of {@link Product}
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProductList() {
        return productDao.findByParams(Collections.<String, String>emptyMap());
    }

    /**
     * Return {@link Product} item with given id
     *
     * @param id given id
     * @return {@link Product} item
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductById(@PathParam("id") Long id) {
        return productDao.findById(id);
    }

    /**
     * Create given {@link Product}
     *
     * @param product given {@link Product}
     * @return created {@link Product}
     */
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Product createProduct(Product product) {
        return productDao.create(product);
    }

    /**
     * Update {@link Product} item with given id
     *
     * @param id      given id of {@link Product}
     * @param product updated {@link Product} data
     * @return updated {@link Product}
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Product updateProduct(@PathParam("id") Long id, Product product) {
        product.setId(id);
        return productDao.update(product);
    }

    /**
     * Delete {@link Product} item with given id
     *
     * @param id given id of {@link Product}
     * @return deleted {@link Product}
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Product deleteProduct(@PathParam("id") Long id) {
        return productDao.delete(id);
    }
}
