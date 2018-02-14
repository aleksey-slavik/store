package com.globallogic.store.dto;

import com.globallogic.store.domain.product.Product;

import javax.validation.constraints.NotNull;

public class ProductDto {

    private long id;

    @NotNull
    private String name;

    @NotNull
    private String brand;
    private String description;

    @NotNull
    private double price;

    public ProductDto() {
    }

    public ProductDto(long id, String name, String brand, String description, double price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
    }

    public ProductDto(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getDescription(),
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
