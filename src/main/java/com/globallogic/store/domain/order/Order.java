package com.globallogic.store.domain.order;

import com.globallogic.store.domain.Identifiable;
import com.globallogic.store.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain object that represents a order entity
 *
 * @author oleksii.slavik
 */
@Entity
@Table(name = "order", schema = "public")
public class Order implements Serializable, Identifiable {

    /**
     * order id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private long id;

    /**
     * order customer
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User customer;

    /**
     * order items
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = OrderItem.class, orphanRemoval = true, mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>(0);

    /**
     * order bill
     */
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Bill bill;

    /**
     * order created date
     */
    @Column(name = "created", nullable = false)
    private long createdDate;

    /**
     * order total cost
     */
    @Column(name = "total_cost", nullable = false)
    private double totalCost;

    /**
     * order status
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * @return entity id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id entity id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return order customer
     */
    public User getCustomer() {
        return customer;
    }

    /**
     * @param customer order customer
     */
    public void setCustomer(User customer) {
        this.customer = customer;
    }

    /**
     * @return order items
     */
    public List<OrderItem> getItems() {
        return items;
    }

    /**
     * @param items order items
     */
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    /**
     * @return order bill
     */
    public Bill getBill() {
        return bill;
    }

    /**
     * @param bill order bill
     */
    public void setBill(Bill bill) {
        this.bill = bill;
    }

    /**
     * @return order created date
     */
    public long getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate order created date
     */
    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return order total cost
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * @param totalCost order total cost
     */
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * @return order status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status order status
     */
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
