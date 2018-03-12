package com.globallogic.store.dto.order;

import com.globallogic.store.domain.order.Order;
import com.globallogic.store.domain.order.OrderItem;
import com.globallogic.store.domain.order.Status;
import com.globallogic.store.domain.user.User;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO extends ResourceSupport {

    private long orderId;

    private String customer;

    private long customerId;

    @Min(value = 0, message = "Created time of order must be positive value")
    private long createdDate;

    @Min(value = 0, message = "Total cost of order must be positive value")
    private double totalCost;

    @NotNull(message = "Status of order cannot be null")
    private Status status;

    private List<OrderItemDTO> items = new ArrayList<>(0);

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

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public Order toOrigin() {
        Order order = new Order();
        order.setId(orderId);
        User user = new User();
        user.setId(customerId);
        user.setUsername(customer);
        order.setCustomer(user);
        order.setTotalCost(totalCost);
        order.setCreatedDate(createdDate);
        order.setStatus(status);
        List<OrderItem> items = new ArrayList<>();

        for (OrderItemDTO itemDto : getItems()) {
            OrderItem item = itemDto.toOrigin(order);
            items.add(item);
        }

        order.setItems(items);
        order.checkTotalCost();
        return order;
    }
}
