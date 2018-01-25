package com.globallogic.store.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a Order
 *
 * @author oleksii.slavik
 */
public class Order extends Entity {

    private Double totalCost;
    private User user;
    private Status status;
    private Set<OrderItem> items = new HashSet<OrderItem>(0);

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "totalCost=" + totalCost +
                ", user=" + user +
                ", status=" + status +
                ", items=" + items +
                '}';
    }
}
