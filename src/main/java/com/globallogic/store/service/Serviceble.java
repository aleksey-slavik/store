package com.globallogic.store.service;

import com.globallogic.store.dao.criteria.SearchCriteria;

import java.util.List;

public interface Serviceble<E> {

    E getById(long id);
    List<E> getList(SearchCriteria criteria);
    E insert(E entity);
    E update(E entity);
    E remove(E entity);
}
