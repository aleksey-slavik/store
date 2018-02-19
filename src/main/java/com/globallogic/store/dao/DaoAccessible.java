package com.globallogic.store.dao;

import java.util.List;
import java.util.Map;

/**
 * Interface which consist methods for work with database.
 *
 * @param <E> type of entity
 * @param <K> type of key of entity
 * @author oleksii.slavik
 */
public interface DaoAccessible<E, K> {

    /**
     * Find entity by given item key.
     *
     * @param key given key
     * @return entity with given key
     */
    E entityByKey(K key);

    /**
     * Find entity by given parameters.
     *
     * @param params given parameters
     * @return entity with given parameters
     */
    E entityByValue(Map<String, Object> params);

    List<E> entityList(int offset, int limit, String sort, String order);

    List<E> entityListByValue(Map<String, Object> params, int offset, int limit, String sort, String order);

    E createEntity(E entity);

    E updateEntity(E entity);

    E deleteEntity(K id);
}
