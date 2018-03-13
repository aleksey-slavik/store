package com.globallogic.store.converter.order;

import com.globallogic.store.converter.Convertible;
import com.globallogic.store.domain.order.Order;
import com.globallogic.store.domain.order.OrderItem;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.dto.order.OrderDTO;
import com.globallogic.store.dto.order.OrderItemDTO;

import java.util.List;

/**
 * Converter for converting {@link Order} objects to {@link OrderDTO} objects and back
 *
 * @author oleksii.slavik
 */
public class OrderConverter implements Convertible<Order, OrderDTO> {

    @Override
    public Order toOrigin(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        User user = new User();
        user.setId(dto.getCustomerId());
        user.setUsername(dto.getCustomer());
        order.setCustomer(user);
        order.setTotalCost(dto.getTotalCost());
        order.setCreatedDate(dto.getCreatedDate());
        order.setStatus(dto.getStatus());
        List<OrderItem> items = new OrderItemConverter().toOrigins(dto.getItems());
        order.setItems(items);
        order.checkTotalCost();
        return order;
    }

    @Override
    public OrderDTO toResource(Order origin) {
        OrderDTO dto = new OrderDTO();
        dto.setCreatedDate(origin.getCreatedDate());
        dto.setCustomer(origin.getCustomer().getUsername());
        dto.setCustomerId(origin.getCustomer().getId());
        dto.setId(origin.getId());
        dto.setStatus(origin.getStatus());
        List<OrderItemDTO> items = new OrderItemConverter().toResources(origin.getItems());
        dto.setItems(items);
        dto.setTotalCost(origin.getTotalCost());
        return dto;
    }
}
