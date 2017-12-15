package com.globallogic.store.dao;

import java.io.Serializable;

public interface UserDAOInterface <T, Id  extends Serializable, V> extends DAOInterface<T, Id> {
    public T verify(V item);
}
