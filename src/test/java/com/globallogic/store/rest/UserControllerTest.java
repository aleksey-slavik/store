package com.globallogic.store.rest;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.rest.spring.UserSpringRestController;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class UserControllerTest {

    private MockMvc mvc;

    @Mock
    private GenericDao<User> userDao;

    @InjectMocks
    private UserSpringRestController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
}
