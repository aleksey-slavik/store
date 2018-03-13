package com.globallogic.store.domain;

/**
 * Interface to identicate entities for access control list
 *
 * @author oleksii.slavik
 */
public interface Identifiable {

    /**
     * @return entity id
     */
    long getId();

    /**
     * @param id entity id
     */
    void setId(long id);
}
