package com.globallogic.store.rest.spring;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.exception.EmptyResponseException;
import com.globallogic.store.exception.NotFoundException;
import com.globallogic.store.model.Product;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Spring rest controller for {@link Product}.
 *
 * @author oleksii.slavik
 */
@RestController
public class ProductSpringRestController {

    /**
     * {@link Product} DAO object for access to database.
     */
    private GenericDao<Product> productDao;

    /**
     * Injection {@link Product} DAO object for access to database.
     */
    public void setProductDao(GenericDao<Product> productDao) {
        this.productDao = productDao;
    }

    /**
     * Return {@link Product} item with given id
     *
     * @param id given id
     * @return {@link Product} item
     * @throws NotFoundException throws when item with given id not found
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProductById(@PathVariable Long id) {
        Product product = productDao.entityByKey(id);

        if (product == null) {
            throw new NotFoundException();
        } else {
            return product;
        }
    }

    /**
     * Return list of {@link Product} represented as json.
     *
     * @param params map of request parameters
     * @return list of {@link Product}
     * @throws EmptyResponseException throws if request parameters is absent
     * @throws NotFoundException      throws when item with parameters not found
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> findProduct(@RequestParam MultiValueMap<String, String> params) {
        if (params.isEmpty()) {
            throw new EmptyResponseException();
        }

        List<Product> products;

        if (params.containsKey("all")) {
            products = productDao.entityList();
        } else {
            products = productDao.entityListByValue(params.toSingleValueMap());
        }

        if (products == null || products.isEmpty()) {
            throw new NotFoundException();
        } else {
            return products;
        }
    }

    /**
     * Create given {@link Product}
     *
     * @param product given {@link Product}
     * @return created {@link Product}
     */
    @RequestMapping(value = "/products", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product createProduct(@RequestBody Product product) {
        return productDao.createEntity(product);
    }

    /**
     * Update {@link Product} item with given id
     *
     * @param id      given id of {@link Product}
     * @param product updated {@link Product} data
     * @return updated {@link Product}
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productDao.updateEntity(product);
    }

    /**
     * Delete {@link Product} item with given id
     *
     * @param id given id of {@link Product}
     * @return deleted {@link Product}
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product deleteProductById(@PathVariable Long id) {
        return productDao.deleteEntity(id);
    }
}
