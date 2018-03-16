package com.globallogic.store.dto.permission;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Sid permission data transfer object
 *
 * @author oleksii.slavik
 */
public class SidDTO {

    /**
     * permission sid
     */
    @NotNull(message = "Permission sid cannot be null")
    @Size(max = 30, message = "Username must be less than {max} characters")
    private String sid;

    /**
     * @return permission sid
     */
    public String getSid() {
        return sid;
    }

    /**
     * @param sid permission sid
     */
    public void setSid(String sid) {
        this.sid = sid;
    }
}
