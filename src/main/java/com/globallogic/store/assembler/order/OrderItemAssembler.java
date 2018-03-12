package com.globallogic.store.assembler.order;

import com.globallogic.store.domain.order.OrderItem;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.dto.order.OrderItemDto;
import com.globallogic.store.rest.OrderRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class OrderItemAssembler extends ResourceAssemblerSupport<OrderItem, OrderItemDto> {

    public OrderItemAssembler() {
        super(OrderRestController.class, OrderItemDto.class);
    }

    @Override
    public OrderItemDto toResource(OrderItem orderItem) {
        OrderItemDto dto = new OrderItemDto();
        dto.setProductId(orderItem.getProduct().getId());
        dto.setName(orderItem.getProduct().getName());
        dto.setBrand(orderItem.getProduct().getBrand());
        dto.setPrice(orderItem.getPrice());
        dto.setQuantity(orderItem.getQuantity());
        return dto;
    }

    public OrderItem toResource(OrderItemDto dto) {
        OrderItem item = new OrderItem();
        Product product = new Product();
        product.setId(dto.getProductId());
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        item.setProduct(product);
        item.setPrice(dto.getPrice());
        item.setQuantity(dto.getQuantity());
        return item;
    }
}
