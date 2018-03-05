package com.globallogic.store.rest.controller;

import com.globallogic.store.assembler.product.ProductAssembler;
import com.globallogic.store.assembler.product.ProductPreviewAssembler;
import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.SearchCriteria;
import com.globallogic.store.dto.product.ProductDto;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.rest.validation.RestValidator;
import com.globallogic.store.security.acl.AclSecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/products")
public class ProductRestController {

    private AclSecurityUtil aclSecurityUtil;

    private GenericDao<Product> productDao;

    private ProductPreviewAssembler previewAssembler;

    private ProductAssembler productAssembler;

    public ProductRestController(
            AclSecurityUtil aclSecurityUtil,
            GenericDao<Product> productDao,
            ProductPreviewAssembler previewAssembler,
            ProductAssembler productAssembler) {
        this.aclSecurityUtil = aclSecurityUtil;
        this.productDao = productDao;
        this.previewAssembler = previewAssembler;
        this.productAssembler = productAssembler;
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
            return ResponseEntity.ok().body(productAssembler.toResource(product));
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
     * @param order order value
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

        SearchCriteria criteria = new SearchCriteria()
                .criteria("name", (Object[]) name)
                .criteria("brand", (Object[]) brand)
                .criteria("price", (Object[]) price)
                .offset(page)
                .limit(size)
                .sortBy(sort)
                .order(order);

        List<Product> products = productDao.getEntityList(criteria);

        if (products == null || products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok().body(previewAssembler.toResources(products));
        }
    }

    /**
     * Create given {@link Product}
     *
     * @param product given {@link Product}
     * @return created {@link Product}
     */
    //@PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody ProductDto product) {
        return new RestValidator<ProductDto>().validate(product, () -> {
            Product created = productDao.createEntity(productAssembler.toResource(product));
            return ResponseEntity.ok().body(created);
        });
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(
            value = "/{id}/share/{username}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sharePermissions(@PathVariable long id, @PathVariable String username, @RequestBody Product product) {
        if (id == product.getId())
            aclSecurityUtil.addPermission(product, username, BasePermission.WRITE, Product.class);
        return ResponseEntity.ok().build();
    }

    /**
     * Update {@link Product} item with given id
     *
     * @param id      given id of {@link Product}
     * @param product updated {@link Product} data
     * @return updated {@link Product}
     */
    //@PreAuthorize("hasRole('ADMIN') || hasPermission(#product, 'write')")
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto product, @PathVariable Long id) {
        return new RestValidator<ProductDto>().validate(product, () -> {
            product.setProductId(id);
            Product updated = productDao.updateEntity(productAssembler.toResource(product));
            return ResponseEntity.ok(updated);
        });
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
}
