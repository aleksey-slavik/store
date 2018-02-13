package com.globallogic.store.domain.orders.cart;

import com.globallogic.store.domain.product.Product;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cart_item")
public class CartItem implements Serializable {

    @EmbeddedId
    private CartItemId primaryKey = new CartItemId();

    @MapsId("cardId")
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cart cart;

    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    public CartItem() {}

    public CartItem(Cart cart, Product product, int quantity) {
        this.primaryKey = new CartItemId(cart.getId(), product.getId());
        this.cart = cart;
        this.product = product;
        this.price = product.getPrice();
        this.quantity = quantity;
    }

    public CartItemId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(CartItemId primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        getPrimaryKey().setCartId(cart.getId());
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        getPrimaryKey().setProductId(product.getId());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItem cartItem = (CartItem) o;

        return getPrimaryKey() != null ? getPrimaryKey().equals(cartItem.getPrimaryKey()) : cartItem.getPrimaryKey() == null;
    }

    @Override
    public int hashCode() {
        return (getPrimaryKey() != null ? getPrimaryKey().hashCode() : 0);
    }
}
