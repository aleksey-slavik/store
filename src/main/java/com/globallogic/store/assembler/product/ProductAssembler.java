package com.globallogic.store.assembler.product;

import com.globallogic.store.domain.product.Product;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.dto.product.ProductDTO;
import com.globallogic.store.rest.ProductRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class ProductAssembler extends ResourceAssemblerSupport<Product, ProductDTO> {

    public ProductAssembler() {
        super(ProductRestController.class, ProductDTO.class);
    }

    @Override
    public ProductDTO toResource(Product origin) {
        ProductDTO dto = createResourceWithId(origin.getId(), origin);
        dto.setProductId(origin.getId());
        dto.setName(origin.getName());
        dto.setBrand(origin.getBrand());
        dto.setDescription(origin.getDescription());
        dto.setPrice(origin.getPrice());
        dto.setOwnerId(origin.getOwner().getId());
        dto.setOwner(origin.getOwner().getUsername());
        return dto;
    }

    public Product toResource(ProductDTO dto) {
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
