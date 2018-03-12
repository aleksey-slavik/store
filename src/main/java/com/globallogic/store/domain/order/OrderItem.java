package com.globallogic.store.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.globallogic.store.domain.product.Product;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Simple JavaBean domain object that represents a item of {@link Order}
 *
 * @author oleksii.slavik
 */
@Entity
@Table(name = "order_item", schema = "public")
public class OrderItem implements Serializable {

    @EmbeddedId
    private OrderItemId primaryKey;

    @JsonIgnore
    @MapsId("orderId")
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Order order;

    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Product product;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int quantity;

    public OrderItem() {}

    public OrderItem(Order order, Product product, int quantity) {
        this.primaryKey = new OrderItemId(order.getId(), product.getId());
        this.order = order;
        this.product = product;
        this.price = product.getPrice();
        this.quantity = quantity;
    }

    public OrderItemId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(OrderItemId primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        getPrimaryKey().setOrderId(order.getId());
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        getPrimaryKey().setProductId(product.getId());
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem item = (OrderItem) o;

        return primaryKey != null ? primaryKey.equals(item.primaryKey) : item.primaryKey == null;
    }

    @Override
    public int hashCode() {
        return primaryKey != null ? primaryKey.hashCode() : 0;
    }
}
