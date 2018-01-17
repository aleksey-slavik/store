package com.globallogic.store.model;

/**
 * Simple JavaBean domain object that represents a {@link Order} status
 *
 * @author oleksii.slavik
 */
public class Status extends Entity {

    private String name;

    public Status() {
        this("OPENED");
    }

    public Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
