package com.globallogic.store.domain.orders.order;

import com.globallogic.store.domain.product.Product;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Simple JavaBean domain object that represents a item of {@link Order}
 *
 * @author oleksii.slavik
 */
@Entity
@Table(name = "order_item")
public class OrderItem implements Serializable {

    @EmbeddedId
    private OrderItemId primaryKey;

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
        getPrimaryKey().setOrderId(product.getId());
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

        OrderItem that = (OrderItem) o;

        return getPrimaryKey() != null ? getPrimaryKey().equals(that.getPrimaryKey()) : that.getPrimaryKey() == null;
    }

    @Override
    public int hashCode() {
        return getPrimaryKey() != null ? getPrimaryKey().hashCode() : 0;
    }
}
