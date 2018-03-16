package com.globallogic.store.converter.product;

import com.globallogic.store.converter.Convertible;
import com.globallogic.store.dto.product.SidDTO;

/**
 * Converter for converting sid objects to {@link SidDTO} objects and back
 *
 * @see org.springframework.security.acls.model.Sid
 * @author oleksii.slavik
 */
public class SidConverter implements Convertible<String, SidDTO> {

    @Override
    public String toOrigin(SidDTO dto) {
        return dto.getSid();
    }

    @Override
    public SidDTO toResource(String origin) {
        SidDTO dto = new SidDTO();
        dto.setSid(origin);
        return dto;
    }
}
