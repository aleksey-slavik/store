package com.globallogic.store.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

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
    private Class<T> type;

    public AbstractDAO(Class<T> type) {
        this.type = type;
    }

    /**
     * Find all items in table.
     *
     * @return list of items
     */
    public List<T> findAll() {
        return new TemplateGenericDAO<List<T>>().processQuery(new Queryable<List<T>>() {
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

    /**
     * Find item by given item id.
     *
     * @param id given id
     * @return item with given id
     */
    public T findById(final Long id) {
        return new TemplateGenericDAO<T>().processQuery(new Queryable<T>() {
            public T query(Session session) {
                return session.get(type, id);
            }
        });
    }

    /**
     * Find all items which have given parameters.
     * Parameters map consist column name as key and search parameter as value.
     *
     * @param params given parameters
     * @return list of items with given parameters
     */
    public List<T> findByCriteria(final Map<String, String> params) {
        return new TemplateGenericDAO<List<T>>().processQuery(new Queryable<List<T>>() {
            public List<T> query(Session session) {
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<T> query = builder.createQuery(type);
                Root<T> root = query.from(type);
                query.select(root);
                ArrayList<Predicate> predicates = new ArrayList<Predicate>();

                for (Map.Entry<String, String> entry : params.entrySet()) {
                    predicates.add(builder.equal(root.get(entry.getKey()), entry.getValue()));
                }

                query.where(builder.and(predicates.toArray(new Predicate[]{})));
                Query<T> q = session.createQuery(query);
                return q.getResultList();
            }
        });
    }

    /**
     * Create given item
     *
     * @param entity given item
     * @return created item
     */
    public T create(final T entity) {
        return new TemplateGenericDAO<T>().processQuery(new Queryable<T>() {
            public T query(Session session) {
                session.save(entity);
                return entity;
            }
        });
    }

    /**
     * Update given item
     *
     * @param entity given item
     * @return updated item
     */
    public T update(final T entity) {
        return new TemplateGenericDAO<T>().processQuery(new Queryable<T>() {
            public T query(Session session) {
                session.update(entity);
                return entity;
            }
        });
    }

    /**
     * Delete item with given id
     *
     * @param id given id
     * @return deleted id
     */
    public T delete(final Long id) {
        return new TemplateGenericDAO<T>().processQuery(new Queryable<T>() {
            public T query(Session session) {
                T entity = session.get(type, id);
                session.delete(entity);
                return entity;
            }
        });
    }
}