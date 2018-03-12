package com.globallogic.store.assembler.order;

import com.globallogic.store.domain.order.OrderItem;
import com.globallogic.store.dto.order.OrderItemDTO;
import com.globallogic.store.rest.OrderRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class OrderItemAssembler extends ResourceAssemblerSupport<OrderItem, OrderItemDTO> {

    public OrderItemAssembler() {
        super(OrderRestController.class, OrderItemDTO.class);
    }

    @Override
    public OrderItemDTO toResource(OrderItem origin) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setProductId(origin.getProduct().getId());
        dto.setName(origin.getProduct().getName());
        dto.setBrand(origin.getProduct().getBrand());
        dto.setPrice(origin.getPrice());
        dto.setQuantity(origin.getQuantity());
        return dto;
    }
}
