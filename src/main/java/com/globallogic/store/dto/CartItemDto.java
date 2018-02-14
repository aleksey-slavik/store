package com.globallogic.store.dto;

import com.globallogic.store.domain.orders.cart.CartItem;

public class CartItemDto {

    private long productId;
    private int quantity;

    public CartItemDto() {
    }

    public CartItemDto(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public CartItemDto(CartItem item) {
        this.productId = item.getProduct().getId();
        this.quantity = item.getQuantity();
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
