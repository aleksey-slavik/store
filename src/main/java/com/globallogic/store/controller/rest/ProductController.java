package com.globallogic.store.controller.rest;

import com.globallogic.store.dao.AbstractGenericDAO;
import com.globallogic.store.model.Product;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.Predicate;
import java.util.List;

@RestController
public class ProductController {

    private AbstractGenericDAO<Product> productDao;

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProductById(@PathVariable Long id) {
        return productDao.findById(id);
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProductList() {
        return productDao.findAll();
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product createProduct(@RequestBody Product product) {
        return productDao.create(product);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productDao.update(product);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product deleteProductById(@PathVariable Long id) {
        return productDao.delete(id);
    }

    public void setProductDao(AbstractGenericDAO<Product> productDao) {
        this.productDao = productDao;
    }
}
