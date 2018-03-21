package com.globallogic.store.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-root.xml", "classpath:spring-security.xml"})
@WebAppConfiguration
public class WebSecurityControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mvc;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity(springSecurityFilterChain))
                .build();
    }

    @Test
    @WithMockUser(username = "admin",authorities = {"CUSTOMER", "ADMIN"})
    public void adminAccessTest() throws Exception {
        //urls, to which admin have access
        checkOkStatus("http://localhost:8080/");
        checkOkStatus("http://localhost:8080/home");
        checkOkStatus("http://localhost:8080/product");
        checkOkStatus("http://localhost:8080/user");
        checkOkStatus("http://localhost:8080/account");
        checkOkStatus("http://localhost:8080/order");

        //urls, to which admin don't have access
        checkForbiddenStatus("http://localhost:8080/login");
        checkForbiddenStatus("http://localhost:8080/register");
        checkForbiddenStatus("http://localhost:8080/account/products");
        checkForbiddenStatus("http://localhost:8080/account/orders");
        checkForbiddenStatus("http://localhost:8080/account/shared");
        checkForbiddenStatus("http://localhost:8080/cart");
    }

    @Test
    @WithMockUser(username = "customer", authorities = {"CUSTOMER"})
    public void customerAccessTest() throws Exception {
        //urls, to which customer have access
        checkOkStatus("http://localhost:8080/");
        checkOkStatus("http://localhost:8080/home");
        checkOkStatus("http://localhost:8080/product");
        checkOkStatus("http://localhost:8080/account");
        checkOkStatus("http://localhost:8080/account/products");
        checkOkStatus("http://localhost:8080/account/orders");
        checkOkStatus("http://localhost:8080/account/shared");
        checkOkStatus("http://localhost:8080/cart");

        //urls, to which customer don't have access
        checkForbiddenStatus("http://localhost:8080/user");
        checkForbiddenStatus("http://localhost:8080/order");
        checkForbiddenStatus("http://localhost:8080/login");
        checkForbiddenStatus("http://localhost:8080/register");
    }

    @Test
    @WithAnonymousUser
    public void anonymousAccessTest() throws Exception {
        //urls, to which anonymous user have access
        checkOkStatus("http://localhost:8080/");
        checkOkStatus("http://localhost:8080/home");
        checkOkStatus("http://localhost:8080/product");
        checkOkStatus("http://localhost:8080/login");
        checkOkStatus("http://localhost:8080/register");

        //urls, to which anonymous user don't have access
        checkUnauthorizedStatus("http://localhost:8080/user");
        checkUnauthorizedStatus("http://localhost:8080/order");
        checkUnauthorizedStatus("http://localhost:8080/account");
        checkUnauthorizedStatus("http://localhost:8080/account/products");
        checkUnauthorizedStatus("http://localhost:8080/account/orders");
        checkUnauthorizedStatus("http://localhost:8080/account/shared");
        checkUnauthorizedStatus("http://localhost:8080/cart");
    }

    /**
     * Check unauthorized returned status
     *
     * @param path url path
     */
    private void checkUnauthorizedStatus(String path) throws Exception {
        mvc.perform(get(path))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost:8080/login"));
    }

    /**
     * Check forbidden returned status
     *
     * @param path url path
     */
    private void checkForbiddenStatus(String path) throws Exception {
        mvc.perform(get(path))
                .andExpect(status().isForbidden());
    }

    /**
     * Check ok returned status
     *
     * @param path url path
     */
    private void checkOkStatus(String path) throws Exception {
        mvc.perform(get(path))
                .andExpect(status().isOk());
    }
}
