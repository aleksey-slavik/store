package com.globallogic.store.assembler.order;

import com.globallogic.store.domain.order.Order;
import com.globallogic.store.dto.order.OrderDTO;
import com.globallogic.store.dto.order.OrderItemDTO;
import com.globallogic.store.rest.OrderRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

public class OrderAssembler extends ResourceAssemblerSupport<Order, OrderDTO> {

    public OrderAssembler() {
        super(OrderRestController.class, OrderDTO.class);
    }

    @Override
    public OrderDTO toResource(Order origin) {
        OrderDTO dto = new OrderDTO();
        dto.setCreatedDate(origin.getCreatedDate());
        dto.setCustomer(origin.getCustomer().getUsername());
        dto.setCustomerId(origin.getCustomer().getId());
        dto.setOrderId(origin.getId());
        dto.setStatus(origin.getStatus());
        List<OrderItemDTO> items = new OrderItemAssembler().toResources(origin.getItems());
        dto.setItems(items);
        dto.setTotalCost(origin.getTotalCost());
        return dto;
    }
}
