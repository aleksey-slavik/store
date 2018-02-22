package com.globallogic.store.dao;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * Interface which consist methods for work with database.
 *
 * @param <E> type of entity
 * @param <K> type of key of entity
 * @author oleksii.slavik
 */
public interface DaoAccessible<E, K> {

    E getEntityByKey(K key);

    E getEntity(SearchCriteria criteria);

    List<E> getEntityList(SearchCriteria criteria);

    @PreAuthorize("hasRole('ADMIN')")
    E createEntity(E entity);

    @PreAuthorize("hasRole('ADMIN')")
    E updateEntity(E entity);

    @PreAuthorize("hasRole('ADMIN')")
    E deleteEntity(K id);
}
