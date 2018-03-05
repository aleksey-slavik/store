package com.globallogic.store.rest.controller;

import com.globallogic.store.assembler.product.ProductAssembler;
import com.globallogic.store.assembler.product.ProductPreviewAssembler;
import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.SearchCriteria;
import com.globallogic.store.dto.product.ProductDto;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.rest.validation.RestValidator;
import com.globallogic.store.security.acl.AclSecurityUtil;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Rest controller for operations with products
 *
 * @author oleksii.slavik
 */
@RestController
@RequestMapping(value = "/api/products")
@Api(value = "/api/products", description = "Operations with products")
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

    /**
     * Resource to get a product by id
     *
     * @param id product id
     * @return product with given id
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Resource to get a product by id")
    public ResponseEntity<?> getProductById(@ApiParam(value = "product id", required = true) @PathVariable long id) {
        Product product = productDao.getEntityByKey(id);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok().body(productAssembler.toResource(product));
        }
    }

    /**
     * Resource to get a list of products
     *
     * @param name  name of product
     * @param brand brand of product
     * @param price price of product
     * @param page  page number
     * @param size  count of items per page
     * @param sort  name of sorting column
     * @param order sorting order
     * @return list of products
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Resource to get a list of products")
    public ResponseEntity<?> getProductList(
            @ApiParam(value = "name of product") @RequestParam(value = "name", required = false) String[] name,
            @ApiParam(value = "brand of product") @RequestParam(value = "brand", required = false) String[] brand,
            @ApiParam(value = "price of product") @RequestParam(value = "price", required = false) Double[] price,
            @ApiParam(value = "page number", defaultValue = "1") @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "count of items per page", defaultValue = "5") @RequestParam(value = "size", defaultValue = "5") int size,
            @ApiParam(value = "name of sorting column", defaultValue = "id") @RequestParam(value = "sort", defaultValue = "id") String sort,
            @ApiParam(value = "sorting order", defaultValue = "asc") @RequestParam(value = "order", defaultValue = "asc") String order) {

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
     * Resource to create a product
     *
     * @param product created product object
     * @return created product
     */
    //@PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Resource to create a product")
    public ResponseEntity<?> createProduct(
            @ApiParam(value = "created product object", required = true) @RequestBody ProductDto product) {
        return new RestValidator<ProductDto>().validate(product, () -> {
            Product created = productDao.createEntity(productAssembler.toResource(product));
            return ResponseEntity.ok().body(productAssembler.toResource(created));
        });
    }

    /**
     * Resource to share permissions to other user
     *
     * @param id       product id
     * @param username username of user who will be granted permissions
     * @param product  shared product object
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(
            value = "/{id}/share/{username}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to share permissions to other user",
            notes = "This can only be done by the user with \"administer\" permissions")
    public ResponseEntity<?> sharePermissions(
            @ApiParam(value = "product id", required = true) @PathVariable long id,
            @ApiParam(value = "username of user who will be granted permissions", required = true) @PathVariable String username,
            @ApiParam(value = "shared product object") @RequestBody Product product) {
        if (id == product.getId())
            aclSecurityUtil.addPermission(product, username, BasePermission.WRITE, Product.class);
        return ResponseEntity.ok().build();
    }

    /**
     * Resource to update a product by id
     *
     * @param id      product id
     * @param product updated product object
     * @return updated product object
     */
    //@PreAuthorize("hasRole('ADMIN') || hasPermission(#product, 'write')")
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to update a product by id",
            notes = "This can only be done by the user with \"write\" permissions")
    public ResponseEntity<?> updateProduct(
            @ApiParam(value = "product id", required = true) @PathVariable Long id,
            @ApiParam(value = "updated product object", required = true) @RequestBody ProductDto product) {
        return new RestValidator<ProductDto>().validate(product, () -> {
            product.setProductId(id);
            Product updated = productDao.updateEntity(productAssembler.toResource(product));
            return ResponseEntity.ok(productAssembler.toResource(updated));
        });
    }

    /**
     * Resource to delete a product by id
     *
     * @param id product id
     * @return deleted product object
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to delete a product by id",
            notes = "This can only be done by the user with \"delete\" permissions")
    public ResponseEntity<?> deleteProduct(
            @ApiParam(value = "product id", required = true) @PathVariable Long id) {
        Product deleted = productDao.deleteEntity(id);
        return ResponseEntity.ok(productAssembler.toResource(deleted));
    }
}