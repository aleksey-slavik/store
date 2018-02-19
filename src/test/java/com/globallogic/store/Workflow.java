package com.globallogic.store;

import com.globallogic.store.domain.product.Product;
import com.globallogic.store.domain.user.Authority;
import com.globallogic.store.domain.user.AuthorityName;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.security.AuthenticatedUser;
import com.globallogic.store.security.AuthenticatedUserFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Emulates application flow
 *
 * @author oleksii.slavik
 */
public class Workflow {

    public static User createAdminUser() {
        User user = new User();
        user.setId(2);
        user.setUsername("testAdmin");
        user.setPassword("1111");
        user.setFirstName("test");
        user.setLastName("admin");
        user.setEmail("test.admin@store.com");
        user.setEnabled(true);
        user.setAuthorities(new HashSet<Authority>() {{
            add(createCustomerAuthority());
            add(createAdminAuthority());
        }});
        return user;
    }

    public static User createCustomerUser() {
        User user = new User();
        user.setId(3);
        user.setUsername("testCustomer");
        user.setPassword("qwerty");
        user.setFirstName("test");
        user.setLastName("customer");
        user.setEmail("test.customer@store.com");
        user.setEnabled(true);
        user.setAuthorities(new HashSet<Authority>() {{
            add(createCustomerAuthority());
        }});
        return user;
    }

    public static Product createDummyProduct(long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("Dummy");
        product.setBrand("Dummy");
        product.setDescription("Dummy product item for tests");
        product.setPrice(9999.99);
        return product;
    }

    public static List<Product> createDummyProductList(int size) {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            products.add(createDummyProduct(i));
        }

        return products;
    }

    public static AuthenticatedUser createAdminAuthenticatedUser() {
        return AuthenticatedUserFactory.create(createAdminUser());
    }

    public static AuthenticatedUser createCustomerAuthenticatedUser() {
        return AuthenticatedUserFactory.create(createCustomerUser());
    }

    public static Authority createAdminAuthority() {
        Authority authority = new Authority();
        authority.setId(1);
        authority.setTitle(AuthorityName.ADMIN);
        return authority;
    }

    public static Authority createCustomerAuthority() {
        Authority authority = new Authority();
        authority.setId(2);
        authority.setTitle(AuthorityName.CUSTOMER);
        return authority;
    }
}