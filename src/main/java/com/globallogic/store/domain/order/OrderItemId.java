package com.globallogic.store.domain.order;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Composite id for order items
 *
 * @author oleksii.slavik
 */
@Embeddable
public class OrderItemId implements Serializable {

    /**
     * order id
     */
    private long orderId;

    /**
     * product id
     */
    private long productId;

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
     * @return product id
     */
    public long getProductId() {
        return productId;
    }

    /**
     * @param productId product id
     */
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
