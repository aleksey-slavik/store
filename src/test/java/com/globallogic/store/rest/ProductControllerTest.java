package com.globallogic.store.rest;

import com.globallogic.store.Workflow;
import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.rest.spring.ProductSpringRestController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    private MockMvc mvc;

    @Mock
    private GenericDao<Product> productDao;

    @InjectMocks
    private ProductSpringRestController controller;

    private static final String URL_PATH_ROOT = "/api/products/";

    private static final String URL_PATH_GET_BY_ID = URL_PATH_ROOT + "{id}";

    private static final long DUMMY_ID = 1;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void checkFindProductByIdTest() throws Exception {
        Product product = Workflow.createDummyProduct(DUMMY_ID);

        when(productDao.entityByKey(DUMMY_ID))
                .thenReturn(product);

        mvc.perform(get(URL_PATH_GET_BY_ID, DUMMY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is((int) product.getId())))
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.brand", is(product.getBrand())))
                .andExpect(jsonPath("$.description", is(product.getDescription())))
                .andExpect(jsonPath("$.price", is(product.getPrice())));

        verify(productDao, times(1)).entityByKey(DUMMY_ID);
        verifyNoMoreInteractions(productDao);
    }

    @Test
    public void checkFindProductByWrongIdTest() throws Exception {
        when(productDao.entityByKey(eq(DUMMY_ID)))
                .thenReturn(null);

        mvc.perform(get(URL_PATH_ROOT + DUMMY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(productDao, times(1)).entityByKey(DUMMY_ID);
        verifyNoMoreInteractions(productDao);
    }

    @Test
    public void checkFindAllProductsTest() throws Exception {

    }
}
