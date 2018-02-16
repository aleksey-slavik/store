package com.globallogic.store.rest.spring;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dto.product.ProductPreviewDto;
import com.globallogic.store.domain.product.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring rest controller for {@link Product}.
 *
 * @author oleksii.slavik
 */
@RestController
@RequestMapping(value = "/api/products")
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
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductById(@PathVariable long id) {
        Product product = productDao.entityByKey(id);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok().body(product);
        }
    }

    /**
     * Return list of {@link Product} represented as json.
     *
     * @param params map of request parameters
     * @return list of {@link Product}
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findProduct(@RequestParam MultiValueMap<String, Object> params) {
        List<Product> products;

        if (params.isEmpty()) {
            products = productDao.entityList();

            if (products == null || products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.ok().body(createPreviews(products));
            }
        } else {
            products = productDao.entityListByValue(params.toSingleValueMap());

            if (products == null || products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.ok().body(createPreviews(products));
            }
        }


    }

    /**
     * Create given {@link Product}
     *
     * @param product given {@link Product}
     * @return created {@link Product}
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        Product created = productDao.createEntity(product);
        return ResponseEntity.ok().body(created);
    }

    /**
     * Update {@link Product} item with given id
     *
     * @param id      given id of {@link Product}
     * @param product updated {@link Product} data
     * @return updated {@link Product}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable Long id) {
        product.setId(id);
        Product updated = productDao.updateEntity(product);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete {@link Product} item with given id
     *
     * @param id given id of {@link Product}
     * @return deleted {@link Product}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        Product deleted = productDao.deleteEntity(id);
        return ResponseEntity.ok(deleted);
    }

    private List<ProductPreviewDto> createPreviews(List<Product> originals) {
        List<ProductPreviewDto> previews = new ArrayList<>();

        for (Product product : originals) {
            previews.add(new ProductPreviewDto(product));
        }

        return previews;
    }
}
