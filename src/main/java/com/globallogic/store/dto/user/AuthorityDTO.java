package com.globallogic.store.dto.user;

import com.globallogic.store.domain.user.AuthorityName;

import javax.validation.constraints.NotNull;

public class AuthorityDTO {

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
}
