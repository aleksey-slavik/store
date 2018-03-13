package com.globallogic.store.dto.order;

import com.globallogic.store.domain.order.Status;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Order data transfer object
 *
 * @author oleksii.slavik
 */
public class OrderDTO {

    /**
     * order id
     */
    private long id;

    /**
     * order customer username
     */
    @NotNull(message = "Username of customer cannot be null")
    @Size(max = 30, message = "Username of customer must be less than {max} characters")
    private String customer;

    /**
     * order customer id
     */
    private long customerId;

    /**
     * order created date
     */
    @Min(value = 0, message = "Created time of order must be positive value")
    private long createdDate;

    /**
     * order total cost
     */
    @Min(value = 0, message = "Total cost of order must be positive value")
    private double totalCost;

    /**
     * order status
     */
    @NotNull(message = "Status of order cannot be null")
    private Status status;

    /**
     * order items
     */
    private List<OrderItemDTO> items = new ArrayList<>(0);

    /**
     * @return order id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id order id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return order customer username
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * @param customer order customer username
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     * @return order customer id
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId order customer id
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    /**
     * @return order created date (in milliseconds)
     */
    public long getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate order created date (in milliseconds)
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
     * @return order items
     */
    public List<OrderItemDTO> getItems() {
        return items;
    }

    /**
     * @param items order items
     */
    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}
