package com.globallogic.store.rest;

import com.globallogic.store.Workflow;
import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.rest.spring.ProductSpringRestController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring-servlet.xml",
        "file:src/main/webapp/WEB-INF/spring-security.xml"})
public class ProductControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Mock
    private GenericDao<Product> productDao;

    private static final String URL_PATH_ROOT = "/api/products/";

    private static final long DUMMY_ID = 1L;

    private static final double DELTA = 0.001;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void checkProductByIdTest() throws Exception {
        Product product = Workflow.createDummyProduct();

        when(productDao.entityByKey(DUMMY_ID))
                .thenReturn(product);

        mvc.perform(get(URL_PATH_ROOT + DUMMY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.id", is(product.getId())))
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.brand", is(product.getBrand())))
                .andExpect(jsonPath("$.description", is(product.getDescription())))
                .andExpect(jsonPath("$.price", is(product.getPrice())));

        verify(productDao, times(1)).entityByKey(DUMMY_ID);
        verifyNoMoreInteractions(productDao);
    }

    @Test
    public void checkProductByWrongIdTest() throws Exception {
        /*when(productDao.entityByKey(eq(DUMMY_ID)))
                .thenReturn(null);*/

        mvc.perform(get(URL_PATH_ROOT + DUMMY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
