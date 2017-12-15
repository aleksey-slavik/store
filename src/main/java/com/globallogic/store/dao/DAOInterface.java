package com.globallogic.store.dao;

import java.io.Serializable;
import java.util.List;

public interface DAOInterface<T, Id extends Serializable> {

    public List<T> findAll();
    public T findById(Id id);
    public Id create(T entity);
    public void update(T entity);
    public void delete(T entity);
}
