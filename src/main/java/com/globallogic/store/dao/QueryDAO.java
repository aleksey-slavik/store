package com.globallogic.store.dao;

import org.hibernate.Session;

public interface QueryDAO<T> {
    T query(Session session);
}
