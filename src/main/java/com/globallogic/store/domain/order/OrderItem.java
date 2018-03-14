package com.globallogic.store.domain.order;

import com.globallogic.store.domain.product.Product;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Domain object that represents a order item entity
 *
 * @author oleksii.slavik
 */
@Entity
@Table(name = "order_item", schema = "public")
public class OrderItem implements Serializable {

    /**
     * composite order item id
     */
    @EmbeddedId
    private OrderItemId primaryKey = new OrderItemId();

    /**
     * order object
     */
    @MapsId("orderId")
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Order order;

    /**
     * product object
     */
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Product product;

    /**
     * price of one item
     */
    @Column(name = "price")
    private double price;

    /**
     * count of items
     */
    @Column(name = "quantity")
    private int quantity;

    /**
     * @return composite order item id
     */
    public OrderItemId getPrimaryKey() {
        return primaryKey;
    }

    /**
     * @param primaryKey composite order item id
     */
    public void setPrimaryKey(OrderItemId primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * @return order object
     */
    public Order getOrder() {
        return order;
    }

    /**
     * @param order order object
     */
    public void setOrder(Order order) {
        this.order = order;
        getPrimaryKey().setOrderId(order.getId());
    }

    /**
     * @return product object
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product product object
     */
    public void setProduct(Product product) {
        this.product = product;
        getPrimaryKey().setProductId(product.getId());
    }

    /**
     * @return price of one item
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price price of one item
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return count of items
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity count of items
     */
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
