package com.globallogic.store.model;

import java.util.HashSet;
import java.util.Set;

public class Order {

    private Long id;
    private Double totalCost;
    private User user;
    private Status status;
    private Set<Product> items = new HashSet<Product>(0);

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

    public Set<Product> getItems() {
        return items;
    }

    public void setItems(Set<Product> products) {
        this.items = products;
    }
}
