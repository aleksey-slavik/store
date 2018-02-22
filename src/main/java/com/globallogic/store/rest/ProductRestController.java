package com.globallogic.store.rest;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.SearchCriteria;
import com.globallogic.store.dto.product.ProductDto;
import com.globallogic.store.dto.product.ProductPreviewDto;
import com.globallogic.store.domain.product.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
public class ProductRestController {

    private GenericDao<Product> productDao;

    public void setProductDao(GenericDao<Product> productDao) {
        this.productDao = productDao;
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductById(@PathVariable long id) {
        Product product = productDao.getEntityByKey(id);

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
     * @param brand brand of product
     * @param price price of product
     * @param page  number of page
     * @param size  count of product per page
     * @param sort  name of field for sorting
     * @param order orderBy value
     * @return list of {@link Product}
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductList(
            @RequestParam(value = "name", required = false) String[] name,
            @RequestParam(value = "brand", required = false) String[] brand,
            @RequestParam(value = "price", required = false) Double[] price,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "order", defaultValue = "asc") String order) {

        SearchCriteria criteria = new SearchCriteria(page, size, sort, order);
        criteria.addCriteria("name", name);
        criteria.addCriteria("brand", brand);
        criteria.addCriteria("price", price);

        List<Product> products = productDao.getEntityList(criteria);

        if (products == null || products.isEmpty()) {
            HttpStatus status = criteria.isParamsAbsent() ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
            return ResponseEntity.status(status).body(null);
        } else {
            return ResponseEntity.ok().body(createPreviews(products));
        }
    }

    /**
     * Create given {@link Product}
     *
     * @param product given {@link Product}
     * @return created {@link Product}
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDto product, @PathVariable Long id) {
        product.setId(id);
        Product updated = productDao.updateEntity(product.getProduct());
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete {@link Product} item with given id
     *
     * @param id given id of {@link Product}
     * @return deleted {@link Product}
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
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
