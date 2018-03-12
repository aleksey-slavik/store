package com.globallogic.store.domain.order;

import com.globallogic.store.domain.Identifiable;
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
    private User customer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = OrderItem.class, orphanRemoval = true, mappedBy = "order")
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

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
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
        items.clear();
        checkTotalCost();
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
     * Add new order item to current order
     *
     * @param item new order item
     */
    public void appendItem(OrderItem item) {
        items.add(item);
        checkTotalCost();
    }

    /**
     * Update order item in current order
     *
     * @param item updated order item
     */
    public void updateItem(OrderItem item) {
        int index = items.indexOf(item);
        items.set(index, item);
        checkTotalCost();
    }

    /**
     * Delete order item from current order
     *
     * @param item deleted order item
     */
    public void deleteItem(OrderItem item) {
        items.remove(item);
        checkTotalCost();
    }
}
