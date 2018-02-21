package com.globallogic.store.rest;

import com.globallogic.store.Workflow;
import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mvc;

    @Mock
    private GenericDao<User> userDao;

    @InjectMocks
    private UserRestController controller;

    private static final String URL_PATH_ROOT = "/api/users/";

    private static final String URL_PATH_GET_BY_ID = URL_PATH_ROOT + "{id}";

    private static final long DUMMY_ID = 1;

    private static final int LIST_SIZE = 10;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void checkFindUserByIdTest() throws Exception {
        User user = Workflow.createCustomerUser();

        when(userDao.entityByKey(DUMMY_ID))
                .thenReturn(user);

        ResultActions actual = mvc.perform(get(URL_PATH_GET_BY_ID, DUMMY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        assertUser(user, actual);
        verify(userDao, times(1)).entityByKey(DUMMY_ID);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void checkFindUserByWrongIdTest() throws Exception {
        when(userDao.entityByKey(DUMMY_ID))
                .thenReturn(null);

        mvc.perform(get(URL_PATH_ROOT + DUMMY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userDao, times(1)).entityByKey(DUMMY_ID);
        verifyNoMoreInteractions(userDao);
    }

    private void assertUser(User expected, ResultActions actual) throws Exception {
        actual
                .andExpect(jsonPath("$.id", is((int) expected.getId())))
                .andExpect(jsonPath("$.username", is(expected.getUsername())))
                .andExpect(jsonPath("$.password", is(expected.getPassword())))
                .andExpect(jsonPath("$.firstName", is(expected.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expected.getLastName())))
                .andExpect(jsonPath("$.email", is(expected.getEmail())))
                .andExpect(jsonPath("$.enabled", is(expected.isEnabled())));
    }

}
