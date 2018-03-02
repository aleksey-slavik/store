package com.globallogic.store.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.globallogic.store.domain.product.Product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductDto {

    private long id;

    @NotNull(message = "Name of product cannot be null")
    @Size(max = 30, message = "Name of product must be less than {max} characters")
    private String name;

    @NotNull(message = "Brand of product cannot be null")
    @Size(max = 100, message = "Brand of product must be less than {max} characters")
    private String brand;

    @Size(max = 10000, message = "Description of product must be less than {max} characters")
    private String description;

    @Min(value = 0, message = "Price of product must be positive value")
    private double price;

    public ProductDto() {}

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.description = product.getDescription();
        this.price = product.getPrice();
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

    @JsonIgnore
    public Product getProduct() {
        return new Product();
    }
}
