package com.globallogic.store.web.product;

import com.globallogic.store.model.Product;
import com.globallogic.store.web.UpdateTemplate;

import javax.servlet.http.HttpServletRequest;

public class UpdateProduct extends UpdateTemplate<Product> {

    @Override
    public String getRestPath() {
        return "http://localhost:8080/products/";
    }

    @Override
    public String getFormViewName() {
        return "product/update";
    }

    @Override
    public String getSuccessViewName() {
        return "redirect:/product";
    }

    @Override
    public Product getItemFromRequest(HttpServletRequest httpServletRequest) {
        return new Product(httpServletRequest);
    }
}
