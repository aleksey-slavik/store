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

    E entityByValue(Map<String, String> params);

    List<E> entityList();

    List<E> entityListByValue(Map<String, String> params);

    E createEntity(E entity);

    E updateEntity(E entity);

    E deleteEntity(K id);
}
