package com.globallogic.store.service;

public class ProductService {
    private ProductService productDAO;

    public void setProductDAO(ProductService productDAO) {
        this.productDAO = productDAO;
    }

    public ProductService getProductDAO() {
        return productDAO;
    }
}
