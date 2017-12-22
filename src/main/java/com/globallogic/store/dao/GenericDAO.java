package com.globallogic.store.dao;

import java.util.List;

public interface GenericDAO<T> {

    List<T> findAll();

    T findById(Long id);

    Long create(T entity);

    void update(T entity);

    void delete(Long id);
}
