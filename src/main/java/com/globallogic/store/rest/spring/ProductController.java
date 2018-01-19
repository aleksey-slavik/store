package com.globallogic.store.rest.spring;

import com.globallogic.store.dao.AbstractDAO;
import com.globallogic.store.exception.EmptyResponseException;
import com.globallogic.store.exception.NotFoundException;
import com.globallogic.store.model.Product;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Spring rest controller for {@link Product}.
 *
 * @author oleksii.slavik
 */
@RestController
public class ProductController {

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
     * Return {@link Product} item with given id
     *
     * @param id given id
     * @return {@link Product} item
     * @throws NotFoundException throws when item with given id not found
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProductById(@PathVariable Long id) {
        Product product = productDao.findById(id);

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
     * @throws NotFoundException throws when item with parameters not found
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> findProduct(@RequestParam MultiValueMap<String, String> params) {
        if (params.isEmpty()) {
            throw new EmptyResponseException();
        }

        if (params.containsKey("all")) {
            return productDao.findAll();
        }

        List<Product> products = productDao.findByCriteria(params.toSingleValueMap());

        if (products.isEmpty()) {
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
        return productDao.create(product);
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
        return productDao.update(product);
    }

    /**
     * Delete {@link Product} item with given id
     *
     * @param id given id of {@link Product}
     * @return deleted {@link Product}
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product deleteProductById(@PathVariable Long id) {
        return productDao.delete(id);
    }
}
