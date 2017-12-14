package com.globallogic.store.model;

/**
 * Status of order
 *
 * @author oleksii.slavik
 */
public class Status {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
