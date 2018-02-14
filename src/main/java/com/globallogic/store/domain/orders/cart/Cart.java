package com.globallogic.store.domain.orders.cart;

import com.globallogic.store.domain.product.Product;
import com.globallogic.store.domain.user.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = CartItem.class, mappedBy = "cart")
    private List<CartItem> items = new ArrayList<>(0);

    @Column(name = "total_items")
    private int totalItemsCount;

    @Column(name = "total_cost")
    private double totalCost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public int getTotalItemsCount() {
        return totalItemsCount;
    }

    public void setTotalItemsCount(int itemsCount) {
        this.totalItemsCount = itemsCount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isEmpty() {
        return totalItemsCount == 0;
    }

    public void clear() {
        getItems().clear();
        update();
    }

    public void update() {
        int total = 0;
        double cost = 0;

        for (CartItem item : getItems()) {
            total += item.getQuantity();
            cost += item.getQuantity() * item.getPrice();
        }

        setTotalCost(cost);
        setTotalItemsCount(total);
    }

    public void update(Product product, int quantity) {
        CartItem item = new CartItem(this, product, quantity);
        List<CartItem> items = getItems();

        if (items.contains(item)) {
            int index = items.indexOf(item);

            if (quantity > 0) {
                items.get(index).setQuantity(quantity);
            } else {
                items.remove(item);
            }
        } else {
            items.add(item);
        }

        update();
    }
}
