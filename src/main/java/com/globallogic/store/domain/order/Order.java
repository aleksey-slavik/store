package com.globallogic.store.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.globallogic.store.domain.Identifiable;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple JavaBean domain object that represents a Order
 *
 * @author oleksii.slavik
 */
@Entity
@Table(name = "order", schema = "public")
public class Order implements Serializable, Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = OrderItem.class, mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>(0);

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Bill bill;

    @Column(name = "created", nullable = false)
    private long createdDate;

    @Column(name = "total_cost", nullable = false)
    private double totalCost;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Clear list of items of current order
     */
    public void clear() {
        getItems().clear();
        checkTotalCost();
    }

    /**
     * Check is empty list of items of current order
     *
     * @return true, if list id not empty, false, in otherwise
     */
    @JsonIgnore
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Update total cost of current order
     */
    public void checkTotalCost() {
        double cost = 0;

        for (OrderItem item : getItems()) {
            cost += item.getPrice() * item.getQuantity();
        }

        setTotalCost(cost);
    }

    /**
     * Add {@link Product} to current order.
     * Check that same {@link Product} already exist in current order.
     *
     * @param product given {@link Product}
     * @param quantity given quantity of {@link Product}
     */
    public void addProduct(Product product, int quantity) {
        OrderItem newItem = new OrderItem(this, product, quantity);
        List<OrderItem> items = getItems();

        if (items.contains(newItem)) {
            int index = items.indexOf(newItem);
            OrderItem item = items.get(index);

            if (quantity > 0 && item.getPrice() == product.getPrice()) {
                quantity += item.getQuantity();
                item.setQuantity(quantity);
                checkTotalCost();
                return;
            }
        }

        items.add(newItem);
        checkTotalCost();
    }
}
