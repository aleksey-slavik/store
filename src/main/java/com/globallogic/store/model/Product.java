package com.globallogic.store.model;

import javax.servlet.http.HttpServletRequest;

/**
 * Simple JavaBean domain object that represents a Product
 *
 * @author oleksii.slavik
 */
public class Product extends Entity {

    private String name;

    private String brand;

    private String description;

    private Double price;

    public Product() {
    }

    public Product(String name, String brand, String description, Double price) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
    }

    public Product(HttpServletRequest request) {
        this.name = request.getParameter("name");
        this.brand = request.getParameter("brand");
        this.description = request.getParameter("description");
        this.price = Double.valueOf(request.getParameter("price"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
