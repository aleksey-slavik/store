package com.globallogic.store.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.globallogic.store.domain.product.Product;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductDto {

    private long id;

    @NotEmpty
    @Length(max = 30)
    private String name;

    @NotEmpty
    @Length(max = 30)
    private String brand;

    @Length(max = 10000)
    private String description;

    @NotEmpty
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

    /**
     * Get {@link Product} object using current {@link ProductDto} data
     *
     * @return {@link Product} object
     */
    @JsonIgnore
    public Product getProduct() {
        return new Product(
                getId(),
                getName(),
                getBrand(),
                getDescription(),
                getPrice()
        );
    }
}
