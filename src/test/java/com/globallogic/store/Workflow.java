package com.globallogic.store;

import com.globallogic.store.converter.product.ProductConverter;
import com.globallogic.store.converter.product.ProductPreviewConverter;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.domain.user.*;
import com.globallogic.store.dto.product.ProductDTO;
import com.globallogic.store.dto.product.ProductPreviewDTO;
import com.globallogic.store.security.core.AuthenticatedUser;
import com.globallogic.store.security.core.AuthenticatedUserFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
            add(createAuthority(2, AuthorityName.CUSTOMER));
            add(createAuthority(1, AuthorityName.ADMIN));
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
            add(createAuthority(2, AuthorityName.CUSTOMER));
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
        product.setOwner(createCustomerUser());
        return product;
    }

    public static List<Product> createDummyProductList(int size) {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            products.add(createDummyProduct(i));
        }

        return products;
    }

    public static ProductDTO createProductDto(Product product) {
        return new ProductConverter().toResource(product);
    }

    public static List<ProductDTO> createProductDto(List<Product> products) {
        return new ProductConverter().toResources(products);
    }

    public static List<ProductPreviewDTO> createProductPreviewDto(List<Product> products) {
        return new ProductPreviewConverter().toResources(products);
    }

    public static Product createProduct(ProductDTO dto) {
        return new ProductConverter().toOrigin(dto);
    }

    public static List<Product> createProduct(List<ProductDTO> dtos) {
        return new ProductConverter().toOrigins(dtos);
    }

    public static AuthenticatedUser createAdminAuthenticatedUser() {
        return AuthenticatedUserFactory.create(createAdminUser());
    }

    public static AuthenticatedUser createCustomerAuthenticatedUser() {
        return AuthenticatedUserFactory.create(createCustomerUser());
    }

    public static Authority createAuthority(long id, AuthorityName title) {
        Authority authority = new Authority();
        authority.setId(1);
        authority.setTitle(title);
        return authority;
    }

    public static void loginUser() {
        AuthenticatedUser user = createCustomerAuthenticatedUser();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public static void loginAdmin() {
        AuthenticatedUser user = createAdminAuthenticatedUser();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public static void logout() {
        SecurityContextHolder.clearContext();
    }
}
