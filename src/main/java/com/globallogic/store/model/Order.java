package com.globallogic.store.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a Order
 *
 * @author oleksii.slavik
 */
public class Order implements Serializable {

    private Long id;
    private Double totalCost;
    private User user;
    private Status status;
    private Set<OrderItem> items = new HashSet<OrderItem>(0);

    public Order(){}

    public Order(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void checkTotalCost() {
        totalCost = 0D;

        for (OrderItem orderItem : items) {
            totalCost += orderItem.getPrice() * orderItem.getQuantity();
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalCost=" + totalCost +
                ", user=" + user +
                ", status=" + status +
                ", items=" + items +
                '}';
    }
}
