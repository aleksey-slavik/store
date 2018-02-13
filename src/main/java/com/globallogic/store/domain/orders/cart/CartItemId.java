package com.globallogic.store.domain.orders.cart;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CartItemId implements Serializable {

    private long cartId;
    private long productId;

    public CartItemId() {}

    public CartItemId(long cartId, long productId) {
        this.cartId = cartId;
        this.productId = productId;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItemId that = (CartItemId) o;

        return cartId == that.cartId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        int result = (int) (cartId ^ (cartId >>> 32));
        result = 31 * result + (int) (productId ^ (productId >>> 32));
        return result;
    }
}
