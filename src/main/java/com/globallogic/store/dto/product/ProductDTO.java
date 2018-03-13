package com.globallogic.store.dto.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Product data transfer object
 *
 * @author oleksii.slavik
 */
public class ProductDTO {

    /**
     * product id
     */
    private long id;

    /**
     * product name
     */
    @NotNull(message = "Name of product cannot be null")
    @Size(max = 30, message = "Name of product must be less than {max} characters")
    private String name;

    /**
     * product brand
     */
    @NotNull(message = "Brand of product cannot be null")
    @Size(max = 100, message = "Brand of product must be less than {max} characters")
    private String brand;

    /**
     * product description
     */
    @Size(max = 10000, message = "Description of product must be less than {max} characters")
    private String description;

    /**
     * product price
     */
    @Min(value = 0, message = "Price of product must be positive value")
    private double price;

    /**
     * product owner id
     */
    @NotNull(message = "Owner id cannot be null")
    private long ownerId;

    /**
     * product owner username
     */
    @NotNull(message = "Owner username cannot be null")
    @Size(max = 30, message = "Owner username must be less than {max} characters")
    private String owner;

    /**
     * @return product id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id product id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return product brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand product brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description product description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return product owner id
     */
    public long getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId product owner id
     */
    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @return product owner username
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner product owner username
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
}
