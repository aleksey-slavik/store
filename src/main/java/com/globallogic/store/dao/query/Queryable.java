package com.globallogic.store.dao.query;

import org.hibernate.Session;

/**
 * Interface which consist query method
 *
 * @param <T> type of data
 * @author oleksii.slavik
 */
public interface Queryable<T> {

    /**
     * Execute query
     *
     * @param session hibernate session
     * @return result of query
     */
    T query(Session session);
}
