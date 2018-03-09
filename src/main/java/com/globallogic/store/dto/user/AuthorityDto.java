package com.globallogic.store.dto.user;

import com.globallogic.store.domain.user.AuthorityName;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

public class AuthorityDto extends ResourceSupport {

    private long authorityId;

    @NotNull(message = "Authority title cannot be null")
    private AuthorityName title;

    public long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(long authorityId) {
        this.authorityId = authorityId;
    }

    public AuthorityName getTitle() {
        return title;
    }

    public void setTitle(AuthorityName title) {
        this.title = title;
    }
}
