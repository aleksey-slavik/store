package com.globallogic.store.assembler.product;

import com.globallogic.store.domain.product.Product;
import com.globallogic.store.dto.product.ProductPreviewDto;
import com.globallogic.store.rest.controller.ProductRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class ProductPreviewAssembler extends ResourceAssemblerSupport<Product, ProductPreviewDto> {

    public ProductPreviewAssembler() {
        super(ProductRestController.class, ProductPreviewDto.class);
    }

    @Override
    public ProductPreviewDto toResource(Product product) {
        ProductPreviewDto dto = createResourceWithId(product.getId(), product);
        dto.setProductId(product.getId());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        return dto;
    }
}
