package com.globallogic.store.dao;

import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Implementation of {@link DaoAccessible} interface.
 *
 * @param <E> type of entity
 * @author oleksii.slavik
 */
public class GenericDao<E> implements DaoAccessible<E, Long> {

    /**
     * {@link Class} of entity type
     */
    private Class<E> entityClass;

    /**
     * Injecting {@link Class} of entity type
     *
     * @param entityClass {@link Class} of entity type
     */
    public GenericDao(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public E entityByKey(final Long id) {
        return new TemplateGenericDao<E>().processQuery(new Queryable<E>() {
            public E query(Session session) {
                return session.get(entityClass, id);
            }
        });
    }

    public E entityByValue(final Map<String, Object> params) {
        return new TemplateGenericDao<E>().processQuery(new Queryable<E>() {
            public E query(Session session) {
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<E> query = builder.createQuery(entityClass);
                Root<E> root = query.from(entityClass);
                query.select(root);
                ArrayList<Predicate> predicates = new ArrayList<Predicate>();

                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    predicates.add(builder.equal(root.get(entry.getKey()), entry.getValue()));
                }

                query.where(builder.and(predicates.toArray(new Predicate[]{})));
                return session.createQuery(query).getSingleResult();
            }
        });
    }

    public List<E> entityList() {
        return new TemplateGenericDao<List<E>>().processQuery(new Queryable<List<E>>() {
            public List<E> query(Session session) {
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<E> query = builder.createQuery(entityClass);
                Root<E> root = query.from(entityClass);
                query.select(root);

                return session.createQuery(query).getResultList();
            }
        });
    }

    public List<E> entityListByValue(final Map<String, Object> params) {
        return new TemplateGenericDao<List<E>>().processQuery(new Queryable<List<E>>() {
            public List<E> query(Session session) {
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<E> query = builder.createQuery(entityClass);
                Root<E> root = query.from(entityClass);
                query.select(root);
                ArrayList<Predicate> predicates = new ArrayList<Predicate>();

                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    predicates.add(builder.equal(root.get(entry.getKey()), entry.getValue()));
                }

                query.where(builder.and(predicates.toArray(new Predicate[]{})));
                return session.createQuery(query).getResultList();
            }
        });
    }

    public E createEntity(final E entity) {
        return new TemplateGenericDao<E>().processQuery(new Queryable<E>() {
            public E query(Session session) {
                session.save(entity);
                return entity;
            }
        });
    }

    public E updateEntity(final E entity) {
        return new TemplateGenericDao<E>().processQuery(new Queryable<E>() {
            public E query(Session session) {
                session.update(entity);
                return entity;
            }
        });
    }

    public E deleteEntity(final Long id) {
        return new TemplateGenericDao<E>().processQuery(new Queryable<E>() {
            public E query(Session session) {
                E entity = session.get(entityClass, id);
                session.delete(entity);
                return entity;
            }
        });
    }
}