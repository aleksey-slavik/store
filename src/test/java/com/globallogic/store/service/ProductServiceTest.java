package com.globallogic.store.service;

import com.globallogic.store.Workflow;
import com.globallogic.store.converter.product.ProductConverter;
import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.criteria.SearchCriteria;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.dto.product.ProductDTO;
import com.globallogic.store.exception.NoContentException;
import com.globallogic.store.exception.NotAcceptableException;
import com.globallogic.store.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private GenericDao<Product> productDao;

    @Mock
    private UserService userService;

    @Mock
    private ProductConverter productConverter;

    @InjectMocks
    private ProductService productService;

    private static final long PRODUCT_ID = 1;

    @Test
    public void findProductByIdTest() throws Exception {
        Product expected = Workflow.createDummyProduct(PRODUCT_ID);
        when(productDao.getEntityByKey(PRODUCT_ID)).thenReturn(expected);
        Product actual = productService.getById(PRODUCT_ID);
        assertEquals(expected, actual);
    }

    @Test
    public void productByIdNotFoundTest() throws Exception {
        try {
            when(productDao.getEntityByKey(PRODUCT_ID)).thenReturn(null);
            productService.getById(PRODUCT_ID);
            fail("NotFoundException don't thrown!");
        } catch (NotFoundException e) {
            //expected
        }
    }

    @Test
    public void findProductListTest() throws Exception {
        List<Product> expected = Workflow.createDummyProductList(5);
        when(productDao.getEntityList(any(SearchCriteria.class))).thenReturn(expected);
        List<Product> actual = productService.getList(new SearchCriteria());
        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void emptyProductListTest() throws Exception {
        try {
            when(productDao.getEntityList(any(SearchCriteria.class))).thenReturn(null);
            productService.getList(new SearchCriteria());
            fail("NoContentException don't thrown!");
        } catch (NoContentException e) {
            //expected
        }

        try {
            when(productDao.getEntityList(any(SearchCriteria.class))).thenReturn(new ArrayList<>());
            productService.getList(new SearchCriteria());
            fail("NoContentException don't thrown!");
        } catch (NoContentException e) {
            //expected
        }
    }

    @Test
    public void insertProductTest() throws Exception {
        Workflow.loginUser();
        User owner = Workflow.createCustomerUser();
        Product expected = Workflow.createDummyProduct(PRODUCT_ID);
        ProductDTO dto = Workflow.createProductDto(expected);
        when(userService.getById(any(Long.class))).thenReturn(owner);
        when(productDao.createEntity(expected)).thenReturn(expected);
        when(productConverter.toOrigin(dto)).thenReturn(expected);
        Product actual = productService.insert(dto);
        assertEquals(expected, actual);
        Workflow.logout();
    }

    @Test
    public void insertProductWithoutOwnerTest() throws Exception {
        try {
            Workflow.loginUser();
            Product product = Workflow.createDummyProduct(PRODUCT_ID);
            ProductDTO dto = Workflow.createProductDto(product);
            when(userService.getById(any(Long.class))).thenThrow(new NotFoundException(""));
            productService.insert(dto);
            fail("NotAcceptableException don't thrown!");
        } catch (NotAcceptableException e) {
            Workflow.logout();
        }
    }

    @Test
    public void updateProductTest() throws Exception {
        Product expected = Workflow.createDummyProduct(PRODUCT_ID);
        ProductDTO dto = Workflow.createProductDto(expected);
        when(productDao.getEntityByKey(PRODUCT_ID)).thenReturn(expected);
        when(productDao.updateEntity(expected)).thenReturn(expected);
        when(productConverter.toOrigin(dto)).thenReturn(expected);
        Product actual = productService.update(PRODUCT_ID, dto);
        assertEquals(expected, actual);
    }

    @Test
    public void updateNonExistentProductTest() throws Exception {
        try {
            Product product = Workflow.createDummyProduct(PRODUCT_ID);
            ProductDTO dto = Workflow.createProductDto(product);
            when(productDao.getEntityByKey(PRODUCT_ID)).thenReturn(null);
            productService.update(PRODUCT_ID, dto);
            fail("NotAcceptableException don't thrown!");
        } catch (NotAcceptableException e) {
            //expected
        }

        try {
            Product product = Workflow.createDummyProduct(PRODUCT_ID);
            ProductDTO dto = Workflow.createProductDto(product);
            when(userService.getById(any(Long.class))).thenThrow(new NotFoundException(""));
            productService.update(PRODUCT_ID, dto);
            fail("NotAcceptableException don't thrown!");
        } catch (NotAcceptableException e) {
            //expected
        }
    }

    @Test
    public void removeProductTest() throws Exception {
        Product expected = Workflow.createDummyProduct(PRODUCT_ID);
        when(productDao.getEntityByKey(PRODUCT_ID)).thenReturn(expected);
        when(productDao.deleteEntity(expected)).thenReturn(expected);
        Product actual = productService.remove(PRODUCT_ID);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteNonExistentProductTest() throws Exception {
        try {
            when(userService.getById(any(Long.class))).thenThrow(new NotFoundException(""));
            productService.remove(PRODUCT_ID);
            fail("NotFoundException don't thrown!");
        } catch (NotFoundException e) {
            //expected
        }
    }
}
