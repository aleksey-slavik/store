package com.globallogic.store.web;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * UI web controller test
 *
 * @author oleksii.slavik
 */
public class WebViewControllerTest {

    private MockMvc mvc;

    /**
     * user web controller
     */
    @InjectMocks
    private UserWebController userWebController;

    /**
     * product web controller
     */
    @InjectMocks
    private ProductWebController productWebController;

    /**
     * order web controller
     */
    @InjectMocks
    private OrderWebController orderWebController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(userWebController, productWebController, orderWebController).build();
    }

    /**
     * Check returned view
     */
    @Test
    public void webControllerTest() throws Exception {
        //product web controller
        webControllerTest("http://localhost:8080/", "product/products");
        webControllerTest("http://localhost:8080/home", "product/products");
        webControllerTest("http://localhost:8080/product", "product/products");

        //user web controller
        webControllerTest("http://localhost:8080/user", "user/users");
        webControllerTest("http://localhost:8080/login", "user/login");
        webControllerTest("http://localhost:8080/register", "user/register");
        webControllerTest("http://localhost:8080/account", "user/account");
        webControllerTest("http://localhost:8080/account/products", "user/products");
        webControllerTest("http://localhost:8080/account/orders", "user/orders");
        webControllerTest("http://localhost:8080/account/shared", "user/shared");

        //order web controller
        webControllerTest("http://localhost:8080/order", "order/orders");
        webControllerTest("http://localhost:8080/cart", "order/cart");
    }

    /**
     * Check returned view name and status
     *
     * @param path url path
     * @param view view name
     */
    private void webControllerTest(String path, String view) throws Exception {
        mvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(view().name(view));
    }
}
