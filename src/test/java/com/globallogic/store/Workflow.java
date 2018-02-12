package com.globallogic.store;

import com.globallogic.store.model.Role;
import com.globallogic.store.model.User;
import com.globallogic.store.security.AuthenticatedUser;
import com.globallogic.store.security.AuthenticatedUserFactory;
import com.globallogic.store.security.jwt.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Emulates application flow
 *
 * @author oleksii.slavik
 */
public class Workflow {

    public static User createAdminUser() {
        User user = new User();
        user.setId(2L);
        user.setUsername("testAdmin");
        user.setPassword("1111");
        user.setFirstName("test");
        user.setLastName("admin");
        user.setEmail("test.admin@store.com");
        user.setRole(Role.ADMIN);
        return user;
    }

    public static User createCustomerUser() {
        User user = new User();
        user.setId(3L);
        user.setUsername("testCustomer");
        user.setPassword("qwerty");
        user.setFirstName("test");
        user.setLastName("customer");
        user.setEmail("test.customer@store.com");
        user.setRole(Role.CUSTOMER);
        return user;
    }

    public static AuthenticatedUser createAdminAuthenticatedUser() {
        return AuthenticatedUserFactory.create(createAdminUser());
    }

    public static AuthenticatedUser createCustomerAuthenticatedUser() {
        return AuthenticatedUserFactory.create(createCustomerUser());
    }
}
