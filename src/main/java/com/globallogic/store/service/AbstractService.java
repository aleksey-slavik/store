package com.globallogic.store.service;

import com.globallogic.store.dao.GenericDAO;

import java.io.Serializable;
import java.util.List;

public class AbstractService<T, ID extends Serializable> {

    private GenericDAO<T, ID> genericDao;

    public void setAbstractDao(GenericDAO genericDao) {
        this.genericDao = genericDao;
    }

    public GenericDAO getAbstractDao() {
        return genericDao;
    }


    public List<T> findAll() {
        return genericDao.findAll();
    }
}
