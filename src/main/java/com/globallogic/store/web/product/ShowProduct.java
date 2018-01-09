package com.globallogic.store.web.product;

import com.globallogic.store.model.Product;
import com.globallogic.store.web.ShowTemplate;

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
