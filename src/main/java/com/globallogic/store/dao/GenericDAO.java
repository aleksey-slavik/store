package com.globallogic.store.dao;

import java.util.List;
import java.util.Map;

public interface GenericDAO<T> {

    List<T> findAll();

    T findById(Long id);

    List<T> findByCriteria(Map<String, String> params);

    Long create(T entity);

    void update(T entity);

    void delete(Long id);
}
