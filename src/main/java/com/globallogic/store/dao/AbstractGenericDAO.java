package com.globallogic.store.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.core.GenericTypeResolver;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractGenericDAO<T, Id extends Serializable> implements GenericDAO<T, Id> {

    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public List<T> findAll() {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getGenericType());
        Root<T> root = query.from(getGenericType());
        query.select(root);
        Query<T> q = session.createQuery(query);
        return q.getResultList();
    }

    public T findById(Id id) {
        return session.get(getGenericType(), id);
    }

    public Id create(T entity) {
        return (Id) session.save(entity);
    }

    public void update(T entity) {
        session.update(entity);
    }

    public void delete(T entity) {
        session.delete(entity);
    }

    private Class<T> getGenericType() {
        return (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractGenericDAO.class);
    }
}
