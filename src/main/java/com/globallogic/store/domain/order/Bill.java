package com.globallogic.store.domain.order;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bill", schema = "public")
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
    private String cardNumber;

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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cartNumber) {
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
