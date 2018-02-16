package com.globallogic.store.dto.product;

import com.globallogic.store.domain.product.Product;

public class ProductPreviewDto {

    private long id;
    private String name;
    private String brand;
    private double price;

    public ProductPreviewDto() {
    }

    public ProductPreviewDto(long id, String name, String brand, double price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public ProductPreviewDto(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getPrice()
        );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
