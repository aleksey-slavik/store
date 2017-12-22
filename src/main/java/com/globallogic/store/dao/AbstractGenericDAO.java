package com.globallogic.store.dao;

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
        return new FindAllHelper().list;
    }

    public T findById(Long id) {
        return new FindByIdHelper(id).item;
    }

    public List<T> findByCriteria(Map<String, String> params) {
        return new FindByCriteriaHelper(params).results;
    }

    private class FindAllHelper extends TemplateGenericDAO {

        private List<T> list;

        public FindAllHelper() {
            processQuery();
        }

        public void query() {
            CriteriaBuilder builder = super.getSession().getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);
            query.select(root);
            Query<T> q = super.getSession().createQuery(query);
            list = q.getResultList();
        }
    }

    private class FindByIdHelper extends TemplateGenericDAO {

        private T item;
        private Long id;

        public FindByIdHelper(Long id) {
            this.id = id;
            processQuery();
            Collections.sort(new ArrayList<String>(), new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return 0;
                }
            });
        }

        public void query() {
            item = super.getSession().get(type, id);
        }
    }

    public Long create(T entity) {
        return new CreateHelper(entity).id;
    }

    public void update(T entity) {
        new UpdateHelper(entity);
    }//todo

    public void delete(Long id) {
        new DeleteHelper(id);
    }//todo

    private class FindByCriteriaHelper extends TemplateGenericDAO {

        private List<T> results;
        private Map<String, String> params;

        public FindByCriteriaHelper(Map<String, String> params) {
            this.params = params;
            processQuery();
        }

        public void query() {
            CriteriaBuilder builder = super.getSession().getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);
            query.select(root);

            Predicate predicate = null;

            for (Map.Entry<String, String> entry : params.entrySet()) {
                predicate = builder.and(builder.equal(root.get(entry.getKey()), entry.getValue()));
            }

            query.where(predicate);
            Query<T> q = super.getSession().createQuery(query);
            results = q.getResultList();
        }
    }

    private class CreateHelper extends TemplateGenericDAO {

        private Long id;
        private T item;

        public CreateHelper(T item) {
            this.item = item;
            processQuery();
        }

        public void query() {
            id = (Long) super.getSession().save(item);
        }
    }

    private class UpdateHelper extends TemplateGenericDAO {

        private T item;

        public UpdateHelper(T item) {
            this.item = item;
            processQuery();
        }

        public void query() {
            getSession().update(item);
        }
    }

    private class DeleteHelper extends TemplateGenericDAO {

        private Long id;

        public DeleteHelper(Long id) {
            this.id = id;
            processQuery();
        }

        public void query() {
            T entity = super.getSession().load(type, id);
            super.getSession().delete(entity);
        }
    }
}
