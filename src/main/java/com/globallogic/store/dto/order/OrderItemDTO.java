package com.globallogic.store.dto.order;

import com.globallogic.store.domain.order.Order;
import com.globallogic.store.domain.order.OrderItem;
import com.globallogic.store.domain.product.Product;
import org.springframework.hateoas.ResourceSupport;

public class OrderItemDTO extends ResourceSupport {

    private long productId;

    private String name;

    private String brand;

    private double price;

    private int quantity;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderItem toOrigin(Order order) {
        Product product = new Product();
        product.setId(productId);
        product.setName(name);
        product.setBrand(brand);
        product.setPrice(price);
        return new OrderItem(order, product, quantity);
    }
}
