package com.globallogic.store.converter.product;

import com.globallogic.store.converter.Convertible;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.dto.product.ProductDTO;

/**
 * Converter for converting {@link Product} objects to {@link ProductDTO} objects and back
 *
 * @author oleksii.slavik
 */
public class ProductConverter implements Convertible<Product, ProductDTO> {

    @Override
    public Product toOrigin(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
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

    @Override
    public ProductDTO toResource(Product origin) {
        ProductDTO dto = new ProductDTO();
        dto.setId(origin.getId());
        dto.setName(origin.getName());
        dto.setBrand(origin.getBrand());
        dto.setDescription(origin.getDescription());
        dto.setPrice(origin.getPrice());
        dto.setOwnerId(origin.getOwner().getId());
        dto.setOwner(origin.getOwner().getUsername());
        return dto;
    }
}
