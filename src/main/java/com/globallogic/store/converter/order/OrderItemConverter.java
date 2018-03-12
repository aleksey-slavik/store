package com.globallogic.store.converter.order;

import com.globallogic.store.converter.Convertible;
import com.globallogic.store.domain.order.OrderItem;
import com.globallogic.store.dto.order.OrderItemDTO;

/**
 * Created by oleksii.slavik on 3/12/2018.
 */
public class OrderItemConverter implements Convertible<OrderItem, OrderItemDTO> {
    @Override
    public OrderItem toOrigin(OrderItemDTO dto) {
        return null;
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
