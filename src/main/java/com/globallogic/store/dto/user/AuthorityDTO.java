package com.globallogic.store.dto.user;

import com.globallogic.store.domain.user.AuthorityName;

import javax.validation.constraints.NotNull;

/**
 * User authority data transfer object
 *
 * @author oleksii.slavik
 */
public class AuthorityDTO {

    /**
     * authority id
     */
    private long id;

    /**
     * authority title
     */
    @NotNull(message = "Authority title cannot be null")
    private AuthorityName title;

    /**
     * @return authority id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id authority id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return authority title
     */
    public AuthorityName getTitle() {
        return title;
    }

    /**
     * @param title authority title
     */
    public void setTitle(AuthorityName title) {
        this.title = title;
    }
}
