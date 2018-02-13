package com.globallogic.store.domain.orders;

import com.globallogic.store.domain.orders.order.Order;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Table(name = "bill")
public class Bill implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "order"))
    private long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Order order;

    @Column(name = "card", nullable = false)
    @NotNull
    @Pattern(regexp = "\\b(?:\\d[ -]*?){13,16}\\b")
    private int cardNumber;

    @Column(name = "total_cost", nullable = false)
    private double totalCost;

    @Column(name = "payed", nullable = false)
    private boolean isPayed;

    @Column(name = "created", nullable = false)
    private long createdDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cartNumber) {
        this.cardNumber = cartNumber;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }
}
