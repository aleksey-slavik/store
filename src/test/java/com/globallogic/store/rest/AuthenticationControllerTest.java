package com.globallogic.store.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.store.Workflow;
import com.globallogic.store.model.User;
import com.globallogic.store.security.AuthenticatedUser;
import com.globallogic.store.security.dto.AuthenticationRequest;
import com.globallogic.store.security.jwt.TokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
                "file:src/main/webapp/WEB-INF/spring-servlet.xml",
                "file:src/main/webapp/WEB-INF/spring-security.xml"})
public class AuthenticationControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private TokenUtil tokenUtil;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @WithAnonymousUser
    public void createAuthenticationTokenTest() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("admin", "admin");
        mvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    public void refreshCustomerAuthenticationTokenTest() throws Exception {
        User user = Workflow.createCustomerUser();
        AuthenticatedUser authenticatedUser = Workflow.createCustomerAuthenticatedUser();

        when(tokenUtil.getUsernameFromToken(any())).thenReturn(user.getUsername());
        when(userDetailsService.loadUserByUsername(eq(user.getUsername()))).thenReturn(authenticatedUser);
        when(tokenUtil.validateToken(any(), any())).thenReturn(true);

        mvc.perform(get("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Auth-Header", "5d1103e-b3e1-4ae9-b606-46c9c1bc915a"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void refreshAdminAuthenticationTokenTest() throws Exception {
        User user = Workflow.createAdminUser();
        AuthenticatedUser authenticatedUser = Workflow.createAdminAuthenticatedUser();

        when(tokenUtil.getUsernameFromToken(any())).thenReturn(user.getUsername());
        when(userDetailsService.loadUserByUsername(eq(user.getUsername()))).thenReturn(authenticatedUser);
        when(tokenUtil.validateToken(any(), any())).thenReturn(true);

        mvc.perform(get("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Auth-Header", "5d1103e-b3e1-4ae9-b606-46c9c1bc915a"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithAnonymousUser
    public void refreshAnonymousAuthenticationTokenTest() throws Exception {
        mvc.perform(get("/api/auth"))
                .andExpect(status().isUnauthorized());
    }
}
