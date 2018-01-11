package com.globallogic.store.web.product;

import com.globallogic.store.model.Product;
import com.globallogic.store.web.ShowTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class ShowProduct extends ShowTemplate<Product> {

    @Override
    public String getRestPath() {
        return "http://localhost:8080/products/";
    }

    @Override
    public String getSimpleItemViewName() {
        return "product/item";
    }

    @Override
    public String getListItemViewName() {
        return "product/list";
    }
}
