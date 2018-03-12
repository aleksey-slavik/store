package com.globallogic.store.converter.user;

import com.globallogic.store.converter.Convertible;
import com.globallogic.store.domain.user.Authority;
import com.globallogic.store.dto.user.AuthorityDTO;

public class AuthorityConverter implements Convertible<Authority, AuthorityDTO> {

    @Override
    public Authority toOrigin(AuthorityDTO dto) {
        Authority authority = new Authority();
        authority.setId(dto.getId());
        authority.setTitle(dto.getTitle());
        return authority;
    }

    @Override
    public AuthorityDTO toResource(Authority origin) {
        AuthorityDTO dto = new AuthorityDTO();
        dto.setId(origin.getId());
        dto.setTitle(origin.getTitle());
        return dto;
    }
}
