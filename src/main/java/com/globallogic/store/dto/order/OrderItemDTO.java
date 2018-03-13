package com.globallogic.store.dto.order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Order item data transfer object
 *
 * @author oleksii.slavik
 */
public class OrderItemDTO {

    /**
     * order id
     */
    private long orderId;

    /**
     * order product id
     */
    private long productId;

    /**
     * order product name
     */
    @NotNull(message = "Name of product of order cannot be null")
    @Size(max = 30, message = "Name of product of order must be less than {max} characters")
    private String name;

    /**
     * order product name
     */
    @NotNull(message = "Brand of product of order cannot be null")
    @Size(max = 100, message = "Brand of product of order must be less than {max} characters")
    private String brand;

    /**
     * order item price
     */
    @Min(value = 0, message = "Price of product of order must be positive value")
    private double price;

    /**
     * count of order items
     */
    @Min(value = 1, message = "Quantity of products of order must be greater than one")
    private int quantity;

    /**
     * @return order id
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId order id
     */
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return order product id
     */
    public long getProductId() {
        return productId;
    }

    /**
     * @param productId order product id
     */
    public void setProductId(long productId) {
        this.productId = productId;
    }

    /**
     * @return order product name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name order product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return order product brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand order product brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return order item price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price order item price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return order item quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity order item quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
