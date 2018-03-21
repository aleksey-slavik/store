package com.globallogic.store.rest;

import com.globallogic.store.converter.product.ProductConverter;
import com.globallogic.store.converter.product.ProductPreviewConverter;
import com.globallogic.store.converter.permission.SidConverter;
import com.globallogic.store.dao.criteria.SearchCriteria;
import com.globallogic.store.domain.permission.PermissionName;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.dto.product.ProductDTO;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.dto.permission.SidDTO;
import com.globallogic.store.exception.AlreadyExistException;
import com.globallogic.store.exception.NoContentException;
import com.globallogic.store.exception.NotAcceptableException;
import com.globallogic.store.exception.NotFoundException;
import com.globallogic.store.security.service.PermissionService;
import com.globallogic.store.service.ProductService;
import com.globallogic.store.service.UserService;
import io.swagger.annotations.*;
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
     * product service
     */
    private ProductService productService;

    /**
     * user service
     */
    private UserService userService;

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
            ProductService productService,
            UserService userService,
            PermissionService permissionService,
            ProductPreviewConverter previewConverter,
            ProductConverter productConverter,
            SidConverter sidConverter) {
        this.productService = productService;
        this.userService = userService;
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
     * @throws NotFoundException thrown when product with given id not found
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Resource to get a product by id")
    public ResponseEntity<?> getProductById(
            @ApiParam(value = "product id", required = true) @PathVariable long id) throws NotFoundException {

        Product product = productService.getById(id);
        return ResponseEntity.ok().body(productConverter.toResource(product));
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
     * @throws NotFoundException  thrown when product owner is not exist
     * @throws NoContentException thrown when products with given criteria not found
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
            @ApiParam(value = "sorting order", defaultValue = "asc") @RequestParam(value = "order", defaultValue = "asc") String order) throws NotFoundException, NoContentException {

        User user = owner != null ? userService.getByUsername(owner) : null;

        SearchCriteria criteria = new SearchCriteria()
                .criteria("name", (Object[]) name)
                .criteria("brand", (Object[]) brand)
                .criteria("price", (Object[]) price)
                .criteria("owner", user)
                .offset(page)
                .limit(size)
                .sortBy(sort)
                .order(order);

        List<Product> products = productService.getList(criteria);
        return ResponseEntity.ok().body(previewConverter.toResources(products));
    }

    /**
     * Resource to get a list of products, to which current user have permissions
     *
     * @param name  name of product
     * @param brand brand of product
     * @param price price of product
     * @param page  page number
     * @param size  count of items per page
     * @param sort  name of sorting column
     * @param order sorting order
     * @return list of products
     * @throws NoContentException throws when available object list for given user is empty
     */
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
            @ApiParam(value = "sorting order", defaultValue = "asc") @RequestParam(value = "order", defaultValue = "asc") String order) throws NoContentException {

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

        List<Product> products = productService.getList(criteria);
        return ResponseEntity.ok().body(previewConverter.toResources(products));
    }

    /**
     * Resource to create a product
     *
     * @param product created product object
     * @return created product
     * @throws NotAcceptableException thrown when product owner not exist in database
     * @throws AlreadyExistException  throws when permission already exist in database
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Resource to create a product",
            notes = "This can only be done by the authenticated user")
    public ResponseEntity<?> createProduct(
            @ApiParam(value = "created product object", required = true) @Valid @RequestBody ProductDTO product) throws NotAcceptableException, AlreadyExistException {

        Product created = productService.insert(productConverter.toOrigin(product));
        permissionService.addPermission(created, PermissionName.ADMINISTRATION, Product.class);
        return ResponseEntity.ok().body(productConverter.toResource(created));
    }

    /**
     * Resource to update a product by id
     *
     * @param id      product id
     * @param product updated product object
     * @return updated product object
     * @throws NotAcceptableException thrown when product with given id or product owner are not exist in database
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
            @ApiParam(value = "updated product object", required = true) @Valid @RequestBody ProductDTO product) throws NotAcceptableException {

        Product updated = productService.update(id, productConverter.toOrigin(product));
        return ResponseEntity.ok(productConverter.toResource(updated));
    }

    /**
     * Resource to delete a product by id
     *
     * @param id product id
     * @return deleted product object
     * @throws NotFoundException thrown when product with given id not exist
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
            @ApiParam(value = "product id", required = true) @PathVariable Long id) throws NotFoundException {

        Product deleted = productService.remove(id);
        permissionService.deletePermission(deleted, Product.class);
        return ResponseEntity.ok(productConverter.toResource(deleted));
    }

    /**
     * Resource to get list if username of users, which have permissions to update (except for merchant of product)
     *
     * @param id product id
     * @throws NotFoundException  throws when product with given id not exist
     * @throws NoContentException throws when user list with needed permissions is empty
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
            @ApiParam(value = "product id", required = true) @PathVariable long id) throws NotFoundException, NoContentException {

        Product product = productService.getById(id);
        List<String> sids = permissionService.getPrincipalList(product, PermissionName.UPDATE, Product.class);
        return ResponseEntity.ok().body(sidConverter.toResources(sids));
    }

    /**
     * Resource to share permissions to other user
     *
     * @param id product id
     * @throws NotFoundException throws when product with given id not exist
     * @throws AlreadyExistException throws when permission already exist in database
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
            @ApiParam(value = "shared product object") @Valid @RequestBody SidDTO sid) throws NotFoundException, AlreadyExistException {

        Product product = productService.getById(id);
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
            @ApiParam(value = "username of user of who will be removed permissions", required = true) @PathVariable String username) throws NotFoundException {

        Product product = productService.getById(id);
        permissionService.deletePermission(product, username, PermissionName.UPDATE, Product.class);
        return ResponseEntity.ok().build();
    }
}
