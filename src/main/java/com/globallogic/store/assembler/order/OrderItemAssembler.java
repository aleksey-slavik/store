package com.globallogic.store.assembler.order;

import com.globallogic.store.domain.order.OrderItem;
import com.globallogic.store.dto.order.OrderItemDto;
import com.globallogic.store.rest.OrderRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class OrderItemAssembler extends ResourceAssemblerSupport<OrderItem, OrderItemDto> {

    public OrderItemAssembler() {
        super(OrderRestController.class, OrderItemDto.class);
    }

    @Override
    public OrderItemDto toResource(OrderItem orderItem) {
        return null;
    }
}
