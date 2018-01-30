package com.globallogic.store.model;

/**
 * Simple JavaBean domain object that represents a item of {@link Order}
 *
 * @author oleksii.slavik
 */
public class OrderItem extends Entity {

    private Product product;
    private Integer quantity;
    private Double price;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + getId() +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
