package com.globallogic.store.dto.user;

import com.globallogic.store.domain.user.Authority;
import com.globallogic.store.domain.user.AuthorityName;
import com.globallogic.store.dto.DTO;

import javax.validation.constraints.NotNull;

public class AuthorityConvertable implements DTO<Authority> {

    private long id;

    @NotNull(message = "Authority title cannot be null")
    private AuthorityName title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AuthorityName getTitle() {
        return title;
    }

    public void setTitle(AuthorityName title) {
        this.title = title;
    }

    @Override
    public Authority toOrigin() {
        Authority authority = new Authority();
        authority.setId(id);
        authority.setTitle(title);
        return authority;
    }

    @Override
    public AuthorityDto toResource(Authority origin) {
        AuthorityDto dto = new AuthorityDto();
        dto.setId(origin.getId());
        dto.setTitle(origin.getTitle());
        return dto;
    }
}
