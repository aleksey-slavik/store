package com.globallogic.store.dao;

import java.util.List;
import java.util.Map;

public interface GenericDAO<T> {

    List<T> findAll();

    T findById(Long id);

    List<T> findByCriteria(Map<String, String> params);

    List<T> findByKey(String key, String... columns);

    T create(T entity);

    T update(T entity);

    T delete(Long id);
}
