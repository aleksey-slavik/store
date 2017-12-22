package com.globallogic.store.dao;

import org.hibernate.Session;

public interface ExecutionCallback<T> {
    T query(Session session);
}
