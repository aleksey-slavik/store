package com.globallogic.store.dao;

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

    E createEntity(E entity);

    E updateEntity(E entity);

    E deleteEntity(K id);
}
