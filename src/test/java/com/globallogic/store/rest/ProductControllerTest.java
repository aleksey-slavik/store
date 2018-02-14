package com.globallogic.store.rest;

import com.globallogic.store.Workflow;
import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.dto.ProductDto;
import com.globallogic.store.dto.ProductPreviewDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

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

        when(productDao.entityByKey(eq(DUMMY_ID)))
                .thenReturn(product);

        Object response = mvc.perform(get(URL_PATH_ROOT + DUMMY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getAsyncResult();

        assertResponse(product, response);
    }

    @Test
    public void checkProductByWrongIdTest() throws Exception {
        when(productDao.entityByKey(eq(DUMMY_ID)))
                .thenReturn(null);

        mvc.perform(get(URL_PATH_ROOT + DUMMY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    private void assertResponse(Product product, Object response) {
        if (response instanceof ProductDto) {
            assertTrue(product.getId() == ((ProductDto) response).getId());
            assertTrue(product.getName().equals(((ProductDto) response).getName()));
            assertTrue(product.getBrand().equals(((ProductDto) response).getBrand()));
            assertTrue(product.getDescription().equals(((ProductDto) response).getDescription()));
            assertEquals(product.getPrice(), ((ProductDto) response).getPrice(), DELTA);
        } else if (response instanceof ProductPreviewDto) {
            assertTrue(product.getId() == ((ProductPreviewDto) response).getId());
            assertTrue(product.getName().equals(((ProductPreviewDto) response).getName()));
            assertTrue(product.getBrand().equals(((ProductPreviewDto) response).getBrand()));
            assertEquals(product.getPrice(), ((ProductPreviewDto) response).getPrice(), DELTA);
        } else {
            fail("Unsupported response type");
        }
    }
}
