package com.globallogic.store.dto.order;

import com.globallogic.store.domain.order.Status;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class OrderDto extends ResourceSupport {

    private long orderId;

    private String customer;

    private long customerId;

    @Min(value = 0, message = "Created time of order must be positive value")
    private long createdDate;

    @Min(value = 0, message = "Total cost of order must be positive value")
    private double totalCost;

    @NotNull(message = "Status of order cannot be null")
    private Status status;

    private List<OrderItemDto> items = new ArrayList<>(0);

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
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

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    /**
     * Update total cost of current order
     */
    public void checkTotalCost() {
        double cost = 0;

        for (OrderItemDto item : getItems()) {
            cost += item.getPrice() * item.getQuantity();
        }

        setTotalCost(cost);
    }

    public OrderItemDto getItemById(long itemId) {
        for (OrderItemDto itemDto : items) {
            if (itemDto.getProductId() == itemId) {
                return itemDto;
            }
        }

        return null;
    }
}
