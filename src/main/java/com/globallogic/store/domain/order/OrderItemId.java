package com.globallogic.store.domain.order;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Composite id for {@link com.globallogic.store.domain.order.OrderItem} table
 *
 * @author oleksii.slavik
 */
@Embeddable
public class OrderItemId implements Serializable {

    private long orderId;
    private long productId;

    public OrderItemId() {
    }

    public OrderItemId(long orderId, long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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

        OrderItemId that = (OrderItemId) o;

        return orderId == that.orderId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        int result = (int) (orderId ^ (orderId >>> 32));
        result = 31 * result + (int) (productId ^ (productId >>> 32));
        return result;
    }
}
