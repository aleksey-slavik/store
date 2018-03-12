package com.globallogic.store.assembler.order;

import com.globallogic.store.domain.order.Order;
import com.globallogic.store.dto.order.OrderDto;
import com.globallogic.store.dto.order.OrderItemDto;
import com.globallogic.store.rest.OrderRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

public class OrderAssembler extends ResourceAssemblerSupport<Order, OrderDto> {

    public OrderAssembler() {
        super(OrderRestController.class, OrderDto.class);
    }

    @Override
    public OrderDto toResource(Order order) {
        OrderDto dto = new OrderDto();
        dto.setCreatedDate(order.getCreatedDate());
        dto.setCustomer(order.getCustomer().getUsername());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setOrderId(order.getId());
        dto.setStatus(order.getStatus());
        List<OrderItemDto> items = new OrderItemAssembler().toResources(order.getItems());
        dto.setItems(items);
        dto.setTotalCost(order.getTotalCost());
        return dto;
    }

    public Order toResource(OrderDto dto) {
        return null;
    }
}
