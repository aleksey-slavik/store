package com.globallogic.store.dto;

import java.util.List;

public class CartDto {

    private String user;
    private int totalItemsCount;
    private int totalCost;
    private List<CartItemDto> items;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getTotalItemsCount() {
        return totalItemsCount;
    }

    public void setTotalItemsCount(int totalItemsCount) {
        this.totalItemsCount = totalItemsCount;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }
}
