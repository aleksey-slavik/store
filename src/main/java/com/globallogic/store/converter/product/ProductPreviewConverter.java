package com.globallogic.store.converter.product;

import com.globallogic.store.converter.Convertible;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.dto.product.ProductPreviewDTO;

/**
 * Created by oleksii.slavik on 3/12/2018.
 */
public class ProductPreviewConverter implements Convertible<Product, ProductPreviewDTO> {
    @Override
    public Product toOrigin(ProductPreviewDTO dto) {
        return null;
    }

    @Override
    public ProductPreviewDTO toResource(Product origin) {
        ProductPreviewDTO dto = new ProductPreviewDTO();
        dto.setProductId(origin.getId());
        dto.setName(origin.getName());
        dto.setBrand(origin.getBrand());
        dto.setPrice(origin.getPrice());
        return dto;
    }
}
