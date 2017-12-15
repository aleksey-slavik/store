package com.globallogic.store.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, ID extends Serializable> {

    List<T> findAll();

    T findById(ID id);

    ID create(T entity);

    void update(T entity);

    void delete(T entity);
}
