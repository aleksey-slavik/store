package com.globallogic.store.dao;

import com.globallogic.store.dao.criteria.SearchCriteria;

import java.util.List;

/**
 * Interface which consist methods for work with database.
 *
 * @param <E> type of entity
 * @param <K> type of key of entity
 * @author oleksii.slavik
 */
public interface DaoAccessible<E, K> {

    /**
     * Get entity by given key
     *
     * @param key given key
     * @return entity with given key
     */
    E getEntityByKey(K key);

    /**
     * Get one entity by given criteria
     *
     * @see SearchCriteria
     * @param criteria given criteria
     * @return entity with given criteria
     */
    E getEntity(SearchCriteria criteria);

    /**
     * Get list of entities by given criteria
     *
     * @see SearchCriteria
     * @param criteria given criteria
     * @return list of entities with given criteria
     */
    List<E> getEntityList(SearchCriteria criteria);

    /**
     * Save given entity in database
     *
     * @param entity given entity
     * @return created entity
     */
    E createEntity(E entity);

    /**
     * Update given entity in database
     *
     * @param entity given entity
     * @return updated entity
     */
    E updateEntity(E entity);

    /**
     * Delete entity with given key from database
     *
     * @param entity given entity
     * @return deleted entity
     */
    E deleteEntity(E entity);
}
