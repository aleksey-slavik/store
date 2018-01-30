package com.globallogic.store.dao;

import com.globallogic.store.filter.SearchFilter;

import java.util.List;
import java.util.Map;

/**
 * Interface which consist methods for work with database.
 *
 * @param <T> type of data
 * @author oleksii.slavik
 */
public interface DAOAccessible<T> {

    /**
     * Find item by given item id.
     *
     * @param id given id
     * @return item with given id
     */
    T findById(Long id);

    /**
     * Find all items which have given parameters.
     * Parameters map consist column name as key and search parameter as value.
     *
     * @param params given parameters
     * @return list of items with given parameters
     */
    List<T> findByParams(Map<String, String> params);

    /**
     * Find all items which have filtered parameters.
     *
     * @param filter filtered parameters
     * @return list of items with given parameters
     */
    List<T> findByFilter(SearchFilter<T> filter);

    /**
     * Create given item
     *
     * @param entity given item
     * @return created item
     */
    T create(T entity);

    /**
     * Update given item
     *
     * @param entity given item
     * @return updated item
     */
    T update(T entity);

    /**
     * Delete item with given id
     *
     * @param id given id
     * @return deleted id
     */
    T delete(Long id);
}
