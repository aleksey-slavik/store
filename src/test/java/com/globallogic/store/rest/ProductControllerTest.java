package com.globallogic.store.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.store.Workflow;
import com.globallogic.store.converter.product.ProductConverter;
import com.globallogic.store.converter.product.ProductPreviewConverter;
import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.dto.product.ProductDTO;
import com.globallogic.store.security.service.PermissionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    private MockMvc mvc;

    @Mock
    private GenericDao<Product> productDao;

    @Mock
    private ProductConverter productConverter;

    @Mock
    private ProductPreviewConverter productPreviewConverter;

    @Mock
    private PermissionService permissionService;

    @InjectMocks
    private ProductRestController controller;

    private static final String URL_PATH_ROOT = "/api/products/";

    private static final String URL_PATH_GET_BY_ID = URL_PATH_ROOT + "{id}";

    private static final long DUMMY_ID = 1;

    private static final int LIST_SIZE = 10;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void checkFindProductByIdTest() throws Exception {
        Product product = Workflow.createDummyProduct(DUMMY_ID);

        when(productDao.getEntityByKey(DUMMY_ID))
                .thenReturn(product);

        when(productConverter.toResource(product))
                .thenReturn(Workflow.createProductDto(product));

        ResultActions actual = mvc.perform(get(URL_PATH_GET_BY_ID, DUMMY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertProduct(product, actual);
        verify(productDao, times(1)).getEntityByKey(DUMMY_ID);
        verifyNoMoreInteractions(productDao);
    }

    @Test
    public void checkFindProductByWrongIdTest() throws Exception {
        when(productDao.getEntityByKey(DUMMY_ID))
                .thenReturn(null);

        mvc.perform(get(URL_PATH_ROOT + DUMMY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(productDao, times(1)).getEntityByKey(DUMMY_ID);
        verifyNoMoreInteractions(productDao);
    }

    @Test
    public void checkFindAllProductsTest() throws Exception {
        List<Product> products = Workflow.createDummyProductList(LIST_SIZE);

        when(productDao.getEntityList(any()))
                .thenReturn(products);

        when(productPreviewConverter.toResources(products))
                .thenReturn(Workflow.createProductPreviewDto(products));

        ResultActions actual = mvc.perform(get(URL_PATH_ROOT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(LIST_SIZE)));

        assertProductList(products, actual);
        verify(productDao, times(1)).getEntityList(any());
        verifyNoMoreInteractions(productDao);
    }

    @Test
    public void checkEmptyProductsTest() throws Exception {
        when(productDao.getEntityList(any()))
                .thenReturn(null);

        mvc.perform(get(URL_PATH_ROOT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(productDao, times(1)).getEntityList(any());
        verifyNoMoreInteractions(productDao);
    }

    @Test
    public void checkCreateProductTest() throws Exception {
        Workflow.loginUser();
        Product product = Workflow.createDummyProduct(DUMMY_ID);
        ProductDTO productDTO = Workflow.createProductDto(product);

        when(productDao.createEntity(any(Product.class)))
                .thenReturn(product);

        when(productConverter.toResource(product))
                .thenReturn(Workflow.createProductDto(product));

        ResultActions actual = mvc.perform(post(URL_PATH_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(productDTO)))
                .andExpect(status().isOk());

        assertProduct(product, actual);
        verify(productDao, times(1)).createEntity(any(Product.class));
        verifyNoMoreInteractions(productDao);
        Workflow.logout();
    }

    @Test
    public void checkUpdateProductTest() throws Exception {
        Product product = Workflow.createDummyProduct(DUMMY_ID);
        ProductDTO productDTO = Workflow.createProductDto(product);

        when(productDao.updateEntity(any(Product.class)))
                .thenReturn(product);

        when(productConverter.toResource(product))
                .thenReturn(Workflow.createProductDto(product));

        ResultActions actual = mvc.perform(put(URL_PATH_GET_BY_ID, DUMMY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(productDTO)))
                .andExpect(status().isOk());

        assertProduct(product, actual);
        verify(productDao, times(1)).updateEntity(any(Product.class));
        verifyNoMoreInteractions(productDao);
    }

    @Test
    public void checkUpdateEmptyProductTest() throws Exception {
        when(productDao.updateEntity(null))
                .thenReturn(null);

        mvc.perform(post(URL_PATH_ROOT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(productDao);
    }

    @Test
    public void checkDeleteProductByIdTest() throws Exception {
        /*Product product = Workflow.createDummyProduct(DUMMY_ID);

        when(productDao.deleteEntity(DUMMY_ID))
                .thenReturn(product);

        when(productConverter.toResource(product))
                .thenReturn(Workflow.createProductDto(product));

        ResultActions actual = mvc.perform(delete(URL_PATH_GET_BY_ID, DUMMY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertProduct(product, actual);
        verify(productDao, times(1)).deleteEntity(DUMMY_ID);
        verifyNoMoreInteractions(productDao);*/
    }

    private void assertProduct(Product expected, ResultActions actual) throws Exception {
        actual
                .andExpect(jsonPath("$.id", is((int) expected.getId())))
                .andExpect(jsonPath("$.name", is(expected.getName())))
                .andExpect(jsonPath("$.brand", is(expected.getBrand())))
                .andExpect(jsonPath("$.description", is(expected.getDescription())))
                .andExpect(jsonPath("$.price", is(expected.getPrice())));
    }

    private void assertProductList(List<Product> expected, ResultActions actual) throws Exception {
        for (int i = 0; i < expected.size(); i++) {
            actual
                    .andExpect(jsonPath("$[" + i + "].id", is((int) expected.get(i).getId())))
                    .andExpect(jsonPath("$[" + i + "].name", is(expected.get(i).getName())))
                    .andExpect(jsonPath("$[" + i + "].brand", is(expected.get(i).getBrand())))
                    .andExpect(jsonPath("$[" + i + "].price", is(expected.get(i).getPrice())));
        }
    }
}
