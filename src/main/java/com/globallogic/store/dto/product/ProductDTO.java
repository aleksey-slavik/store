package com.globallogic.store.dto.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductDTO {

    private long productId;

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

    @NotNull(message = "Owner id cannot be null")
    private long ownerId;

    @NotNull(message = "Owner username cannot be null")
    @Size(max = 30, message = "Owner username must be less than {max} characters")
    private String owner;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
