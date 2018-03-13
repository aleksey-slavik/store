package com.globallogic.store.converter.order;

import com.globallogic.store.converter.Convertible;
import com.globallogic.store.domain.order.Order;
import com.globallogic.store.domain.order.OrderItem;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.dto.order.OrderItemDTO;

/**
 * Converter for converting {@link OrderItem} objects to {@link OrderItemDTO} objects and back
 *
 * @author oleksii.slavik
 */
public class OrderItemConverter implements Convertible<OrderItem, OrderItemDTO> {

    @Override
    public OrderItem toOrigin(OrderItemDTO dto) {
        OrderItem item = new OrderItem();
        Order order = new Order();
        order.setId(dto.getOrderId());
        item.setOrder(order);
        Product product = new Product();
        product.setId(dto.getProductId());
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        item.setProduct(product);
        item.setPrice(dto.getPrice());
        item.setQuantity(dto.getQuantity());
        return null;
    }

    @Override
    public OrderItemDTO toResource(OrderItem origin) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setOrderId(origin.getOrder().getId());
        dto.setProductId(origin.getProduct().getId());
        dto.setName(origin.getProduct().getName());
        dto.setBrand(origin.getProduct().getBrand());
        dto.setPrice(origin.getPrice());
        dto.setQuantity(origin.getQuantity());
        return dto;
    }
}
