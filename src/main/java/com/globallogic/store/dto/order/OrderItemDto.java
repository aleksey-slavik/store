package com.globallogic.store.dto.order;

import org.springframework.hateoas.ResourceSupport;

public class OrderItemDto extends ResourceSupport {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderItemDto itemDto = (OrderItemDto) o;

        return productId == itemDto.productId &&
                Double.compare(itemDto.price, price) == 0 &&
                quantity == itemDto.quantity &&
                (name != null ? name.equals(itemDto.name) : itemDto.name == null) &&
                (brand != null ? brand.equals(itemDto.brand) : itemDto.brand == null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (int) (productId ^ (productId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + quantity;
        return result;
    }
}
