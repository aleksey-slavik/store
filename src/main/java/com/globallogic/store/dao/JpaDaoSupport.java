package com.globallogic.store.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

/**
 * JPA support class
 *
 * @author oleksii.slavik
 */
public class JpaDaoSupport {

    /**
     * {@link EntityManager} persistence object
     */
    private EntityManager entityManager;

    JpaDaoSupport(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Find object of given {@link Class} of entity, which have given unique key (for example, id)
     *
     * @param entityClass given {@link Class} of entity
     * @param key         given key
     * @param <E>         type of returned entity
     * @param <K>         type of key of returned entity
     * @return entity with given key
     */
    public <E, K> E entityByKey(Class<E> entityClass, K key) {
        return entityManager.find(entityClass, key);
    }

    /**
     * Find object of given {@link Class} of entity, which have given value
     *
     * @param entityClass given {@link Class} of entity
     * @param attribute   persistent single-valued property or field
     * @param value       value of property or field
     * @param <E>         type of returned entity
     * @param <V>         type of attribute
     * @return entity with given value
     */
    public <E, V> E entityByValue(Class<E> entityClass, SingularAttribute<? super E, V> attribute, V value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<E> root = rootCriteriaQuery(criteriaQuery, entityClass);
        criteriaQuery.where(criteriaBuilder.equal(root.get(attribute), value));

        try {
            return buildTypedQuery(criteriaQuery, null, null).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Get list of entities using filter, limit and offset.
     *
     * @param entityClass given {@link Class} of entity
     * @param filter      {@link Expression} filter
     * @param limit       limit value
     * @param offset      offset value
     * @param <E>         type of entity
     * @return list of entities
     */
    public <E> List<E> entityList(Class<E> entityClass, Expression<Boolean> filter, Integer limit, Integer offset) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        rootCriteriaQuery(criteriaQuery, entityClass);

        if (filter != null) {
            filterCriteriaQuery(criteriaQuery, filter);
        }

        TypedQuery<E> typedQuery = buildTypedQuery(criteriaQuery, limit, offset);
        return typedQuery.getResultList();
    }

    /**
     * Get list of entities using filter.
     *
     * @param entityClass given {@link Class} of entity
     * @param filter      {@link Expression} filter
     * @param <E>         type of entity
     * @return list of entities
     */
    public <E> List<E> entityList(Class<E> entityClass, Expression<Boolean> filter) {
        return entityList(entityClass, filter, null, null);
    }

    /**
     * Get list of entities.
     *
     * @param entityClass given {@link Class} of entity
     * @param <E>         type of entity
     * @return list of entities
     */
    public <E> List<E> entityList(Class<E> entityClass) {
        return entityList(entityClass, null, null, null);
    }

    /**
     * Find list of given {@link Class} of entities, which have given value
     *
     * @param entityClass type of entity
     * @param attribute   persistent single-valued property or field
     * @param value       value of property or field
     * @param <E>         type of returned entity
     * @param <V>         type of attribute
     * @return list of entities with given value
     */
    public <E, V> List<E> entityListByValue(Class<E> entityClass, SingularAttribute<? super E, V> attribute, V value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<E> root = rootCriteriaQuery(criteriaQuery, entityClass);
        criteriaQuery.where(criteriaBuilder.equal(root.get(attribute), value));
        return buildTypedQuery(criteriaQuery, null, null).getResultList();
    }

    /**
     * Create entity with given data
     *
     * @param entity given entity data
     * @param <E>    type of entity
     */
    public <E> void create(E entity) {
        entityManager.persist(entity);
    }

    /**
     * Update entity data
     *
     * @param entity given entity data
     * @param <E>    type of entity
     */
    public <E> void update(E entity) {
        entityManager.refresh(entity);
    }

    /**
     * Delete entity with given data
     *
     * @param entity given entity data
     * @param <E>    type of entity
     */
    public <E> void delete(E entity) {
        entityManager.remove(entity);
    }

    /**
     * Create query with given limit and offset.
     * Return corresponding {@link TypedQuery}.
     * If offset is null there are no offset.
     * If limit is null there are no limit.
     *
     * @param criteriaQuery {@link CriteriaQuery} query
     * @param limit         limit value
     * @param offset        offset value
     * @param <E>           type of returned entity
     * @return {@link TypedQuery} with given limit and offset if they are not null
     */
    private <E> TypedQuery<E> buildTypedQuery(CriteriaQuery<E> criteriaQuery, Integer limit, Integer offset) {
        TypedQuery<E> typedQuery = entityManager.createQuery(criteriaQuery);

        if (offset != null) {
            typedQuery.setFirstResult(offset);
        }

        if (limit != null) {
            typedQuery.setMaxResults(limit);
        }

        return typedQuery;
    }

    /**
     * Add where clause for given {@link CriteriaQuery} object.
     *
     * @param criteriaQuery given {@link CriteriaQuery} query
     * @param filter        {@link Expression} filter
     */
    private void filterCriteriaQuery(CriteriaQuery<?> criteriaQuery, Expression<Boolean> filter) {
        if (filter != null) {
            criteriaQuery.where(filter);
        }
    }

    /**
     * Create {@link Root} object using {@link CriteriaQuery} query and {@link Class} of entity
     *
     * @param criteriaQuery given {@link CriteriaQuery} query
     * @param entityClass   given {@link Class} of entity
     * @param <E>           type of entity
     * @return {@link Root} object
     */
    private <E> Root<E> rootCriteriaQuery(CriteriaQuery<?> criteriaQuery, Class<E> entityClass) {
        return criteriaQuery.from(entityClass);
    }
}
