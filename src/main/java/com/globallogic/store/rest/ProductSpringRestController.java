package com.globallogic.store.rest;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dto.product.ProductDto;
import com.globallogic.store.dto.product.ProductPreviewDto;
import com.globallogic.store.domain.product.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /*private Validator validator;*/

    /**
     * Injection {@link Product} DAO object for access to database.
     */
    public void setProductDao(GenericDao<Product> productDao) {
        this.productDao = productDao;
    }

    /*public void setValidator(Validator validator) {
        this.validator = validator;
    }
*/
    /*@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }*/

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
     * @param name  name of product
     * @param brand brand pf product
     * @param price price of product
     * @param page  number of page
     * @param size  count of product per page
     * @return list of {@link Product}
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findProduct(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "order", defaultValue = "asc") String order) {

        List<Product> products;

        if (name == null && brand == null && price == null) {
            products = productDao.entityList((page - 1) * size, size, sort, order);

            if (products == null || products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.ok().body(createPreviews(products));
            }
        } else {
            Map<String, Object> params = new HashMap<>();

            if (name != null)
                params.put("name", name);
            if (brand != null)
                params.put("brand", brand);
            if (price != null)
                params.put("price", price);

            products = productDao.entityListByValue(params, (page - 1) * size, size, sort, order);

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
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto product) {
        Product created = productDao.createEntity(product.getProduct());
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
