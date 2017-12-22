package com.globallogic.store.dao;

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
        TemplateGenericDAO<List<T>> templateDAO = new TemplateGenericDAO<List<T>>(new QueryDAO<List<T>>() {
            public List<T> query(Session session) {
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<T> query = builder.createQuery(type);
                Root<T> root = query.from(type);
                query.select(root);
                Query<T> q = session.createQuery(query);
                return q.getResultList();
            }
        });

        return templateDAO.processQuery();
    }

    public T findById(final Long id) {
        TemplateGenericDAO<T> templateDAO = new TemplateGenericDAO<T>(new QueryDAO<T>() {
            public T query(Session session) {
                return session.get(type, id);
            }
        });

        return templateDAO.processQuery();
    }

    public List<T> findByCriteria(final Map<String, String> params) {
        TemplateGenericDAO<List<T>> templateDAO = new TemplateGenericDAO<List<T>>(new QueryDAO<List<T>>() {
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

        return templateDAO.processQuery();
    }

    public T create(final T entity) {
        TemplateGenericDAO<T> templateDAO = new TemplateGenericDAO<T>(new QueryDAO<T>() {
            public T query(Session session) {
                session.save(entity);
                return entity;
            }
        });

        return templateDAO.processQuery();
    }

    public T update(final T entity) {
        TemplateGenericDAO<T> templateDAO = new TemplateGenericDAO<T>(new QueryDAO<T>() {
            public T query(Session session) {
                session.update(entity);
                return entity;
            }
        });
        return templateDAO.processQuery();
    }

    public T delete(final Long id) {
        TemplateGenericDAO<T> templateDAO = new TemplateGenericDAO<T>(new QueryDAO<T>() {
            public T query(Session session) {
                T entity = session.load(type, id);
                session.delete(entity);
                return entity;
            }
        });

        return templateDAO.processQuery();
    }
}