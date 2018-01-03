package com.globallogic.store.rest.jersey;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.model.Product;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/products")
public class ProductController {

    private AbstractGenericDAO<Product> productDao = new AbstractGenericDAO<Product>(Product.class);

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProductList() {
        return productDao.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductById(@PathParam("id") Long id) {
        return productDao.findById(id);
    }

    @GET
    @Path("/search/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> searchProduct(@PathParam("key") String key) {
        return productDao.findByKey(key, "name", "brand", "description");
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Product createProduct(Product product) {
        return productDao.create(product);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Product updateProduct(@PathParam("id") Long id, Product product) {
        product.setId(id);
        return productDao.update(product);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Product deleteProduct(@PathParam("id") Long id) {
        return productDao.delete(id);
    }
}
