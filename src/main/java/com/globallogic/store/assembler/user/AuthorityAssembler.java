package com.globallogic.store.assembler.user;

import com.globallogic.store.domain.user.Authority;
import com.globallogic.store.dto.user.AuthorityDto;
import com.globallogic.store.rest.UserRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class AuthorityAssembler extends ResourceAssemblerSupport<Authority, AuthorityDto> {

    public AuthorityAssembler() {
        super(UserRestController.class, AuthorityDto.class);
    }

    @Override
    public AuthorityDto toResource(Authority authority) {
        AuthorityDto dto = new AuthorityDto();
        dto.setAuthorityId(authority.getId());
        dto.setTitle(authority.getTitle());
        return dto;
    }
}
