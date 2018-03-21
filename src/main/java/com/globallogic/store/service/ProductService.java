package com.globallogic.store.service;

import com.globallogic.store.converter.product.ProductConverter;
import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.criteria.SearchCriteria;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.dto.product.ProductDTO;
import com.globallogic.store.exception.NoContentException;
import com.globallogic.store.exception.NotAcceptableException;
import com.globallogic.store.exception.NotFoundException;
import com.globallogic.store.security.core.AuthenticatedUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Product service
 *
 * @author oleksii.slavik
 */
@Service
public class ProductService {

    /**
     * product DAO
     */
    private GenericDao<Product> productDao;

    /**
     * user service
     */
    private UserService userService;

    /**
     * product converter
     */
    private ProductConverter productConverter;

    public ProductService(
            GenericDao<Product> productDao,
            UserService userService,
            ProductConverter productConverter) {
        this.productDao = productDao;
        this.userService = userService;
        this.productConverter = productConverter;
    }

    /**
     * Get product by given id
     *
     * @param id product id
     * @return product with given id
     * @throws NotFoundException thrown when product with given id not found
     */
    public Product getById(long id) throws NotFoundException {
        Product product = productDao.getEntityByKey(id);

        if (product == null) {
            throw new NotFoundException("Product with id=" + id + " not found!");
        } else {
            return product;
        }
    }

    /**
     * Get list of products considering given criteria
     *
     * @param criteria search criteria
     * @return list of products considering given criteria
     * @throws NoContentException thrown when products with given criteria not found
     */
    public List<Product> getList(SearchCriteria criteria) throws NoContentException {
        List<Product> products = productDao.getEntityList(criteria);

        if (products == null || products.isEmpty()) {
            throw new NoContentException("Products with given criteria not found!");
        } else {
            return products;
        }
    }

    /**
     * Save product in database
     *
     * @param product product object
     * @return created product
     * @throws NotAcceptableException thrown when product owner not exist in database
     */
    public Product insert(ProductDTO product) throws NotAcceptableException {
        try {
            long id = ((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            userService.getById(id);
            product.setOwnerId(id);
            return productDao.createEntity(productConverter.toOrigin(product));
        } catch (NotFoundException e) {
            throw new NotAcceptableException("Can't create product without user owner. User with id=" + product.getOwnerId() + " not found!");
        }
    }

    /**
     * Update product in database
     *
     * @param id      product id
     * @param product product object
     * @return updated product
     * @throws NotAcceptableException thrown when product with given id or product owner are not exist in database
     */
    public Product update(long id, ProductDTO product) throws NotAcceptableException {
        try {
            getById(id);
            userService.getById(product.getOwnerId());
            product.setId(id);
            return productDao.updateEntity(productConverter.toOrigin(product));
        } catch (NotFoundException e) {
            throw new NotAcceptableException("Can't update product with id=" + id + "!");
        }
    }

    /**
     * Remove product from database
     *
     * @param id product id
     * @return removed product
     * @throws NotFoundException thrown when product with given id not exist
     */
    public Product remove(long id) throws NotFoundException {
        Product deleted = getById(id);
        return productDao.deleteEntity(deleted);
    }
}
