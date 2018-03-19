package com.globallogic.store.rest;

import com.globallogic.store.converter.product.ProductConverter;
import com.globallogic.store.converter.product.ProductPreviewConverter;
import com.globallogic.store.converter.permission.SidConverter;
import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.criteria.SearchCriteria;
import com.globallogic.store.domain.permission.PermissionName;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.dto.product.ProductDTO;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.dto.permission.SidDTO;
import com.globallogic.store.security.core.AuthenticatedUser;
import com.globallogic.store.security.service.PermissionService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    /**
     * product DAO
     */
    private GenericDao<Product> productDao;

    /**
     * user DAO
     */
    private GenericDao<User> userDao;

    /**
     * permission service
     */
    private PermissionService permissionService;

    /**
     * product preview converter
     */
    private ProductPreviewConverter previewConverter;

    /**
     * product converter
     */
    private ProductConverter productConverter;

    /**
     * permission converter
     */
    private SidConverter sidConverter;

    public ProductRestController(
            GenericDao<Product> productDao,
            GenericDao<User> userDao,
            PermissionService permissionService,
            ProductPreviewConverter previewConverter,
            ProductConverter productConverter,
            SidConverter sidConverter) {
        this.productDao = productDao;
        this.userDao = userDao;
        this.permissionService = permissionService;
        this.previewConverter = previewConverter;
        this.productConverter = productConverter;
        this.sidConverter = sidConverter;
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
    public ResponseEntity<?> getProductById(
            @ApiParam(value = "product id", required = true) @PathVariable long id) {
        Product product = productDao.getEntityByKey(id);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok().body(productConverter.toResource(product));
        }
    }

    /**
     * Resource to get a list of products
     *
     * @param name  name of product
     * @param brand brand of product
     * @param price price of product
     * @param owner username of product owner
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
            @ApiParam(value = "username of product owner") @RequestParam(value = "owner", required = false) String owner,
            @ApiParam(value = "page number", defaultValue = "1") @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "count of items per page", defaultValue = "5") @RequestParam(value = "size", defaultValue = "5") int size,
            @ApiParam(value = "name of sorting column", defaultValue = "id") @RequestParam(value = "sort", defaultValue = "id") String sort,
            @ApiParam(value = "sorting order", defaultValue = "asc") @RequestParam(value = "order", defaultValue = "asc") String order) {

        User user = null;

        if (owner != null) {
            SearchCriteria ownerCriteria = new SearchCriteria().criteria("username", owner);
            user = userDao.getEntity(ownerCriteria);
        }

        SearchCriteria criteria = new SearchCriteria()
                .criteria("name", (Object[]) name)
                .criteria("brand", (Object[]) brand)
                .criteria("price", (Object[]) price)
                .criteria("owner", user)
                .offset(page)
                .limit(size)
                .sortBy(sort)
                .order(order);

        List<Product> products = productDao.getEntityList(criteria);

        if (products == null || products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok().body(previewConverter.toResources(products));
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = "/shared",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to get a list of products, to which current user have permissions",
            notes = "This can only be done by the authenticated user")
    public ResponseEntity<?> getSharedProductList(
            @ApiParam(value = "name of product") @RequestParam(value = "name", required = false) String[] name,
            @ApiParam(value = "brand of product") @RequestParam(value = "brand", required = false) String[] brand,
            @ApiParam(value = "price of product") @RequestParam(value = "price", required = false) Double[] price,
            @ApiParam(value = "page number", defaultValue = "1") @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "count of items per page", defaultValue = "5") @RequestParam(value = "size", defaultValue = "5") int size,
            @ApiParam(value = "name of sorting column", defaultValue = "id") @RequestParam(value = "sort", defaultValue = "id") String sort,
            @ApiParam(value = "sorting order", defaultValue = "asc") @RequestParam(value = "order", defaultValue = "asc") String order) {

        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Long> ids = permissionService.getIdList(principal, PermissionName.UPDATE, Product.class);

        SearchCriteria criteria = new SearchCriteria()
                .criteria("id", ids.toArray())
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
            return ResponseEntity.ok().body(previewConverter.toResources(products));
        }
    }

    /**
     * Resource to create a product
     *
     * @param product created product object
     * @return created product
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to create a product",
            notes = "This can only be done by the authenticated user")
    public ResponseEntity<?> createProduct(
            @ApiParam(value = "created product object", required = true) @Valid @RequestBody ProductDTO product) {

        long id = ((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        product.setOwnerId(id);
        Product created = productDao.createEntity(productConverter.toOrigin(product));
        permissionService.addPermission(created, PermissionName.ADMINISTRATION, Product.class);
        return ResponseEntity.ok().body(productConverter.toResource(created));
    }

    /**
     * Resource to update a product by id
     *
     * @param id      product id
     * @param product updated product object
     * @return updated product object
     */
    @PreAuthorize("hasAuthority('ADMIN') || hasPermission(#id, 'com.globallogic.store.domain.product.Product', 'ADMINISTRATION') || hasPermission(#id, 'com.globallogic.store.domain.product.Product', 'UPDATE')")
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to update a product by id",
            notes = "This can only be done by the user with \"write\" permissions")
    public ResponseEntity<?> updateProduct(
            @ApiParam(value = "product id", required = true) @PathVariable Long id,
            @ApiParam(value = "updated product object", required = true) @Valid @RequestBody ProductDTO product) {

        product.setId(id);
        Product updated = productDao.updateEntity(productConverter.toOrigin(product));
        return ResponseEntity.ok(productConverter.toResource(updated));
    }

    /**
     * Resource to delete a product by id
     *
     * @param id product id
     * @return deleted product object
     */
    @PreAuthorize("hasAuthority('ADMIN') || hasPermission(#id, 'com.globallogic.store.domain.product.Product', 'ADMINISTRATION')")
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to delete a product by id",
            notes = "This can only be done by the user with \"administration\" permissions")
    public ResponseEntity<?> deleteProduct(
            @ApiParam(value = "product id", required = true) @PathVariable Long id) {

        Product deleted = productDao.deleteEntityByKey(id);
        return ResponseEntity.ok(productConverter.toResource(deleted));
    }

    /**
     * Resource to get list if username of users, which have permissions to update (except for merchant of product)
     *
     * @param id product id
     */
    @PreAuthorize("hasAuthority('ADMIN') || hasPermission(#id, 'com.globallogic.store.domain.product.Product', 'ADMINISTRATION')")
    @RequestMapping(
            value = "/{id}/permissions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to get list if username of users, which have permissions to update (except for merchant of product)",
            notes = "This can only be done by the user with \"administration\" permissions")
    public ResponseEntity<?> getPermissionList(
            @ApiParam(value = "product id", required = true) @PathVariable long id) {

        Product product = productDao.getEntityByKey(id);
        List<String> sids = permissionService.getPrincipalList(product, PermissionName.UPDATE, Product.class);

        if (sids == null || sids.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            sids.remove(product.getOwner().getUsername());
            return ResponseEntity.ok().body(sidConverter.toResources(sids));
        }
    }

    /**
     * Resource to share permissions to other user
     *
     * @param id product id
     */
    @PreAuthorize("hasAuthority('ADMIN') || hasPermission(#id, 'com.globallogic.store.domain.product.Product', 'ADMINISTRATION')")
    @RequestMapping(
            value = "/{id}/permissions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to share permissions to other user",
            notes = "This can only be done by the user with \"administration\" permissions")
    public ResponseEntity<?> sharePermissions(
            @ApiParam(value = "product id", required = true) @PathVariable long id,
            @ApiParam(value = "shared product object") @Valid @RequestBody SidDTO sid) {

        Product product = productDao.getEntityByKey(id);
        permissionService.addPermission(product, sid.getSid(), PermissionName.UPDATE, Product.class);
        return ResponseEntity.ok().build();
    }

    /**
     * Resource to share permissions to other user
     *
     * @param id       product id
     * @param username username of user who will be granted permissions
     */
    @PreAuthorize("hasAuthority('ADMIN') || hasPermission(#id, 'com.globallogic.store.domain.product.Product', 'ADMINISTRATION')")
    @RequestMapping(
            value = "/{id}/permissions/{username}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to remove permissions of other user",
            notes = "This can only be done by the user with \"administration\" permissions")
    public ResponseEntity<?> removePermissions(
            @ApiParam(value = "product id", required = true) @PathVariable long id,
            @ApiParam(value = "username of user of who will be removed permissions", required = true) @PathVariable String username) {

        Product product = productDao.getEntityByKey(id);
        permissionService.deletePermission(product, username, PermissionName.UPDATE, Product.class);
        return ResponseEntity.ok().build();
    }
}
