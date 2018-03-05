package com.globallogic.store.assembler.product;

import com.globallogic.store.domain.product.Product;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.dto.product.ProductDto;
import com.globallogic.store.rest.ProductRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class ProductAssembler extends ResourceAssemblerSupport<Product, ProductDto> {

    public ProductAssembler() {
        super(ProductRestController.class, ProductDto.class);
    }

    @Override
    public ProductDto toResource(Product product) {
        ProductDto dto = createResourceWithId(product.getId(), product);
        dto.setProductId(product.getId());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setOwnerId(product.getOwner().getId());
        dto.setOwner(product.getOwner().getUsername());
        return dto;
    }

    public Product toResource(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getProductId());
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        User user = new User();
        user.setId(dto.getOwnerId());
        user.setUsername(dto.getOwner());
        product.setOwner(user);
        return product;
    }
}
