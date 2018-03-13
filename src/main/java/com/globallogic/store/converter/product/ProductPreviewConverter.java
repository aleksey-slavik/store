package com.globallogic.store.converter.product;

import com.globallogic.store.converter.Convertible;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.dto.product.ProductPreviewDTO;

/**
 * Converter for converting {@link Product} objects to {@link ProductPreviewDTO} objects and back
 *
 * @author oleksii.slavik
 */
public class ProductPreviewConverter implements Convertible<Product, ProductPreviewDTO> {
    @Override
    public Product toOrigin(ProductPreviewDTO dto) {
        return null;
    }

    @Override
    public ProductPreviewDTO toResource(Product origin) {
        ProductPreviewDTO dto = new ProductPreviewDTO();
        dto.setId(origin.getId());
        dto.setName(origin.getName());
        dto.setBrand(origin.getBrand());
        dto.setPrice(origin.getPrice());
        return dto;
    }
}
