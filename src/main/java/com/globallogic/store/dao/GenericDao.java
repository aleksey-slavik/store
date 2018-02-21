package com.globallogic.store.dao;

import javax.persistence.TypedQuery;
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

    public E getEntityByKey(final Long id) {
        return new TemplateGenericDao<E>().processQuery(session -> session.get(entityClass, id));
    }

    public E getEntity(final SearchCriteria criteria) {
        return new TemplateGenericDao<E>().processQuery(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<E> query = builder.createQuery(entityClass);
            Root<E> root = query.from(entityClass);
            query.select(root);
            ArrayList<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, List<Object>> entry : criteria.getParams().entrySet()) {
                ArrayList<Predicate> innerPredicates = new ArrayList<>();

                for (Object value : entry.getValue()) {
                    innerPredicates.add(builder.equal(root.get(entry.getKey()), value));
                }

                predicates.add( builder.or(innerPredicates.toArray(new Predicate[]{})));
            }

            query.where(builder.and(predicates.toArray(new Predicate[]{})));
            return session.createQuery(query).getSingleResult();
        });
    }

    public List<E> entityList(int offset, int limit, String sort, String order) {
        return new TemplateGenericDao<List<E>>().processQuery(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();

            //create select statement for entities
            CriteriaQuery<E> allEntitiesQuery = builder.createQuery(entityClass);
            Root<E> root = allEntitiesQuery.from(entityClass);

            if (order.equalsIgnoreCase("DESC")) {
                allEntitiesQuery.orderBy(builder.desc(root.get(sort)));
            } else {
                allEntitiesQuery.orderBy(builder.asc(root.get(sort)));
            }

            CriteriaQuery<E> selectAll = allEntitiesQuery.select(root);

            //create pagination
            TypedQuery<E> paginationQuery = session.createQuery(selectAll);
            paginationQuery.setFirstResult(offset);
            paginationQuery.setMaxResults(limit);
            return paginationQuery.getResultList();
        });
    }

    public List<E> getEntityList(final SearchCriteria criteria) {
        return new TemplateGenericDao<List<E>>().processQuery(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();

            //create select statement for entities
            CriteriaQuery<E> selectQuery = builder.createQuery(entityClass);
            Root<E> root = selectQuery.from(entityClass);
            selectQuery.select(root);
            ArrayList<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, List<Object>> entry : criteria.getParams().entrySet()) {
                ArrayList<Predicate> innerPredicates = new ArrayList<>();

                for (Object value : entry.getValue()) {
                    innerPredicates.add(builder.equal(root.get(entry.getKey()), value));
                }

                predicates.add( builder.or(innerPredicates.toArray(new Predicate[]{})));
            }

            if (criteria.getOrderBy().equalsIgnoreCase("DESC")) {
                selectQuery.orderBy(builder.desc(root.get(criteria.getSortBy())));
            } else {
                selectQuery.orderBy(builder.asc(root.get(criteria.getSortBy())));
            }
            CriteriaQuery<E> selectAll = selectQuery.where(builder.and(predicates.toArray(new Predicate[]{})));

            //create pagination
            TypedQuery<E> paginationQuery = session.createQuery(selectAll);
            paginationQuery.setFirstResult(criteria.getOffset());
            paginationQuery.setMaxResults(criteria.getLimit());
            return paginationQuery.getResultList();
        });
    }

    public E createEntity(final E entity) {
        return new TemplateGenericDao<E>().processQuery(session -> {
            session.save(entity);
            return entity;
        });
    }

    public E updateEntity(final E entity) {
        return new TemplateGenericDao<E>().processQuery(session -> {
            session.update(entity);
            return entity;
        });
    }

    public E deleteEntity(final Long id) {
        return new TemplateGenericDao<E>().processQuery(session -> {
            E entity = session.get(entityClass, id);
            session.delete(entity);
            return entity;
        });
    }
}