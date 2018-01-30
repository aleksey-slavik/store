package com.globallogic.store.dao;

import com.globallogic.store.filter.SearchFilter;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Implementation of {@link DAOAccessible} interface.
 *
 * @param <T> type of data
 * @author oleksii.slavik
 */
public class AbstractDAO<T> implements DAOAccessible<T> {

    /**
     * Class og injected type
     */
    private Class<T> entityClass;

    public AbstractDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T findById(final Long id) {
        return new TemplateGenericDAO<T>().processQuery(new Queryable<T>() {
            public T query(Session session) {
                return session.get(entityClass, id);
            }
        });
    }

    public List<T> findByParams(final Map<String, String> params) {
        return new TemplateGenericDAO<List<T>>().processQuery(new Queryable<List<T>>() {
            public List<T> query(Session session) {
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<T> query = builder.createQuery(entityClass);
                Root<T> root = query.from(entityClass);
                query.select(root);

                if (params.isEmpty()) {
                    return session.createQuery(query).getResultList();
                } else {
                    ArrayList<Predicate> predicates = new ArrayList<Predicate>();

                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        predicates.add(builder.equal(root.get(entry.getKey()), entry.getValue()));
                    }

                    query.where(builder.and(predicates.toArray(new Predicate[]{})));
                    return  session.createQuery(query).getResultList();
                }
            }
        });
    }

    public List<T> findByFilter(final SearchFilter<T> filter) {
        return new TemplateGenericDAO<List<T>>().processQuery(new Queryable<List<T>>() {
            public List<T> query(Session session) {
                return filter.getResultList(session);
            }
        });
    }

    public T create(final T entity) {
        return new TemplateGenericDAO<T>().processQuery(new Queryable<T>() {
            public T query(Session session) {
                session.save(entity);
                return entity;
            }
        });
    }

    public T update(final T entity) {
        return new TemplateGenericDAO<T>().processQuery(new Queryable<T>() {
            public T query(Session session) {
                session.update(entity);
                return entity;
            }
        });
    }

    public T delete(final Long id) {
        return new TemplateGenericDAO<T>().processQuery(new Queryable<T>() {
            public T query(Session session) {
                T entity = session.get(entityClass, id);
                session.delete(entity);
                return entity;
            }
        });
    }
}