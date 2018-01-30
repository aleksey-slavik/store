package com.globallogic.store.filter;

import org.hibernate.Session;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SearchFilter<T> {
    private Class<T> entityClass;
    private List<Predicate> predicates = new ArrayList<Predicate>(0);
    private CriteriaBuilder criteriaBuilder;

    protected SearchFilter(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    private void addPredicate(Predicate predicate) {
        if (predicates != null) {
            predicates.add(predicate);
        }
    }

    protected <X> void addEqual(Expression<X> expression, X value) {
        if (expression != null && value != null && criteriaBuilder != null) {
            addPredicate(criteriaBuilder.equal(expression, value));
        }
    }

    public List<T> getResultList(Session session) {
        criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        buildPredicates(root);
        criteriaQuery.select(root);
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        return session.createQuery(criteriaQuery).getResultList();
    }

    protected abstract void buildPredicates(Root<T> root);
}
