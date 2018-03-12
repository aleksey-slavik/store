package com.globallogic.store.converter.product;

import com.globallogic.store.converter.Convertible;
import com.globallogic.store.domain.product.Product;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.dto.product.ProductDTO;

/**
 * Created by oleksii.slavik on 3/12/2018.
 */
public class ProductConverter implements Convertible<Product, ProductDTO> {

    @Override
    public Product toOrigin(ProductDTO dto) {
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

    @Override
    public ProductDTO toResource(Product origin) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(origin.getId());
        dto.setName(origin.getName());
        dto.setBrand(origin.getBrand());
        dto.setDescription(origin.getDescription());
        dto.setPrice(origin.getPrice());
        dto.setOwnerId(origin.getOwner().getId());
        dto.setOwner(origin.getOwner().getUsername());
        return dto;
    }
}
