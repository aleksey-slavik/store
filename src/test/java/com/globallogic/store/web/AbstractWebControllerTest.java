package com.globallogic.store.web;

import com.globallogic.store.web.user.UserProductsWebController;
import com.globallogic.store.web.user.UserWebController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.AbstractController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AbstractWebControllerTest {

    @InjectMocks
    private UserWebController userWebController;

    @InjectMocks
    private UserProductsWebController userProductsWebController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    //@Test
    public void webControllerTest() throws Exception {
        webControllerTest(userWebController, "http://localhost:8080/user", "user/users");
        webControllerTest(userProductsWebController, "http://localhost:8080/account/products", "/user/products");
    }

    private void webControllerTest(AbstractController controller, String path, String view) throws Exception {
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();
        mvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(view().name(view));
    }
}
