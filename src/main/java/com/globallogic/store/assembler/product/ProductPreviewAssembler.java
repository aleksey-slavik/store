package com.globallogic.store.assembler.product;

import com.globallogic.store.domain.product.Product;
import com.globallogic.store.dto.product.ProductPreviewDTO;
import com.globallogic.store.rest.ProductRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class ProductPreviewAssembler extends ResourceAssemblerSupport<Product, ProductPreviewDTO> {

    public ProductPreviewAssembler() {
        super(ProductRestController.class, ProductPreviewDTO.class);
    }

    @Override
    public ProductPreviewDTO toResource(Product origin) {
        ProductPreviewDTO dto = createResourceWithId(origin.getId(), origin);
        dto.setProductId(origin.getId());
        dto.setName(origin.getName());
        dto.setBrand(origin.getBrand());
        dto.setPrice(origin.getPrice());
        return dto;
    }
}
