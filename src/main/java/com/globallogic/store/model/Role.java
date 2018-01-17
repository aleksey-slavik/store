package com.globallogic.store.model;

/**
 * Simple JavaBean domain object that represents a {@link User} roles
 *
 * @author oleksii.slavik
 */
public class Role extends Entity {

    private String name;

    public Role() {
        this("CUSTOMER");
    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
