package com.globallogic.store.domain.product;

import com.globallogic.store.domain.Identifiable;
import com.globallogic.store.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Domain object that represents a product entity
 *
 * @author oleksii.slavik
 */
@Entity
@Table(name = "product", schema = "public")
public class Product implements Serializable, Identifiable {

    /**
     * product id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private long id;

    /**
     * product name
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * product brand
     */
    @Column(name = "brand", nullable = false)
    private String brand;

    /**
     * product description
     */
    @Column(name = "description")
    private String description;

    /**
     * product price
     */
    @Column(name = "price", nullable = false)
    private double price;

    /**
     * owner of product
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;

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
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description product description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return product brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand product brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return product owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * @param owner product owner
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }
}
