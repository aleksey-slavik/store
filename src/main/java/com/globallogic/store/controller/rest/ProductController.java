package com.globallogic.store.controller.rest;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.model.Product;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProductById(@PathVariable Long id) {
        AbstractGenericDAO<Product> productDao = new AbstractGenericDAO<Product>(Product.class);
        return productDao.findById(id);
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProductList() {
        AbstractGenericDAO<Product> productDao = new AbstractGenericDAO<Product>(Product.class);
        return productDao.findAll();
    }

    @RequestMapping(value = "/create-product", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Long createProduct(@RequestBody Product product) {
        AbstractGenericDAO<Product> userDao = new AbstractGenericDAO<Product>(Product.class);
        return userDao.create(product);
    }

    @RequestMapping(value = "/update-product", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateProduct(@RequestBody Product product) {
        AbstractGenericDAO<Product> productDao = new AbstractGenericDAO<Product>(Product.class);
        productDao.update(product);
    }

    @RequestMapping(value = "/delete-product/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteProductById(@PathVariable Long id) {
        AbstractGenericDAO<Product> productDao = new AbstractGenericDAO<Product>(Product.class);
        productDao.delete(id);
    }
}
