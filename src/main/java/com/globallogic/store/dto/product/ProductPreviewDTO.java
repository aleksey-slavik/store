package com.globallogic.store.dto.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Product preview data transfer object
 *
 * @author oleksii.slavik
 */
public class ProductPreviewDTO {

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
     * product price
     */
    @Min(value = 0, message = "Price of product must be positive value")
    private double price;

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
}
