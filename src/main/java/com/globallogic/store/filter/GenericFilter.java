package com.globallogic.store.filter;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericFilter<E> {

    private Class<E> entityClass;
    private List<Predicate> predicates;
    private CriteriaBuilder criteriaBuilder;

    public void setEntityClass(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    private void add(Predicate predicate) {
        if (predicates == null) {
            predicates = new ArrayList<>();
        }

        if (predicate != null) {
            predicates.add(predicate);
        }
    }

    public <V> void addPredicate(Expression<V> expression, V value) {
        if (expression != null && value!= null && criteriaBuilder != null) {
            add(criteriaBuilder.and(criteriaBuilder.equal(expression, value)));
        }
    }

    public <V> void addPredicate(Expression<V> expression, V[] values) {
        if (expression != null && values!= null && criteriaBuilder != null) {
            for (V value : values) {
                add(criteriaBuilder.or(criteriaBuilder.equal(expression, value)));
            }
        }
    }

    public abstract void buildPredicates(Root<E> root);

    public List<E> getResultList(EntityManager entityManager) {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = criteriaBuilder.createQuery(entityClass);
        Root<E> root = query.from(entityClass);
        buildPredicates(root);
        query.select(root).where(predicates.toArray(new Predicate[]{}));
        return entityManager.createQuery(query).getResultList();
    }
}
