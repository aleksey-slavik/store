package com.globallogic.store.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

public class AbstractGenericDAO<T> implements GenericDAO<T> {

    private Class<T> type;

    public AbstractGenericDAO(Class<T> type) {
        this.type = type;
    }

    public List<T> findAll() {
        return new TemplateGenericDAO<List<T>>().processQuery(new ExecutionCallback<List<T>>() {
            public List<T> query(Session session) {
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<T> query = builder.createQuery(type);
                Root<T> root = query.from(type);
                query.select(root);
                Query<T> q = session.createQuery(query);
                return q.getResultList();
            }
        });
    }

    public T findById(final Long id) {
        return new TemplateGenericDAO<T>().processQuery(new ExecutionCallback<T>() {
            public T query(Session session) {
                return session.get(type, id);
            }
        });
    }

    public List<T> findByCriteria(final Map<String, String> params) {
        return new TemplateGenericDAO<List<T>>().processQuery(new ExecutionCallback<List<T>>() {
            public List<T> query(Session session) {
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<T> query = builder.createQuery(type);
                Root<T> root = query.from(type);
                query.select(root);
                Predicate predicate = null;

                for (Map.Entry<String, String> entry : params.entrySet()) {
                    predicate = builder.and(builder.equal(root.get(entry.getKey()), entry.getValue()));
                }

                query.where(predicate);
                Query<T> q = session.createQuery(query);
                return q.getResultList();
            }
        });
    }

    public T create(final T entity) {
        return new TemplateGenericDAO<T>().processQuery(new ExecutionCallback<T>() {
            public T query(Session session) {
                session.save(entity);
                return entity;
            }
        });
    }

    public T update(final T entity) {
        return new TemplateGenericDAO<T>().processQuery(new ExecutionCallback<T>() {
            public T query(Session session) {
                session.update(entity);
                return entity;
            }
        });
    }

    public T delete(final Long id) {
        return new TemplateGenericDAO<T>().processQuery(new ExecutionCallback<T>() {
            public T query(Session session) {
                T entity = session.get(type, id);
                session.delete(entity);
                return entity;
            }
        });
    }
}