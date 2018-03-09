package com.globallogic.store.assembler.order;

import com.globallogic.store.domain.order.Order;
import com.globallogic.store.dto.order.OrderDto;
import com.globallogic.store.rest.OrderRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class OrderAssembler extends ResourceAssemblerSupport<Order, OrderDto> {

    public OrderAssembler() {
        super(OrderRestController.class, OrderDto.class);
    }

    @Override
    public OrderDto toResource(Order order) {
        return null;
    }

    public Order toResource(OrderDto dto) {
        return null;
    }
}
