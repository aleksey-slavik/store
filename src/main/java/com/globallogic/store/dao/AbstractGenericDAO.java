package com.globallogic.store.dao;

import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AbstractGenericDAO<T> implements GenericDAO<T> {

    private Class<T> type;

    public AbstractGenericDAO(Class<T> type) {
        this.type = type;
    }

    public List<T> findAll() {
       FindAllHelper findAllHelper = new FindAllHelper();
       findAllHelper.processQuery();
       return findAllHelper.list;
    }

    public T findById(Long id) {
        FindByIdHelper findByIdHelper = new FindByIdHelper(id);
        findByIdHelper.processQuery();
        return findByIdHelper.item;
    }

    public Long create(T entity) {
        CreateHelper createHelper = new CreateHelper(entity);
        createHelper.processQuery();
        return createHelper.id;
    }

    public void update(T entity) {
        UpdateHelper updateHelper = new UpdateHelper(entity);
        updateHelper.processQuery();
    }

    public void delete(Long id) {
        DeleteHelper deleteHelper = new DeleteHelper(id);
        deleteHelper.processQuery();
    }

    private class FindAllHelper extends TemplateGenericDAO {

        private List<T> list;

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
        }

        public void query() {
            item = super.getSession().get(type, id);
        }
    }

    private class CreateHelper extends TemplateGenericDAO {

        private Long id;
        private T item;

        public CreateHelper(T item) {
            this.item = item;
        }

        public void query() {
            id = (Long) super.getSession().save(item);
        }
    }

    private class UpdateHelper extends TemplateGenericDAO {

        private T item;

        public UpdateHelper(T item) {
            this.item = item;
        }

        public void query() {
            getSession().update(item);
        }
    }

    private class DeleteHelper extends TemplateGenericDAO {

        private Long id;

        public DeleteHelper(Long id) {
            this.id = id;
        }

        public void query() {
            T entity = super.getSession().load(type, id);
            super.getSession().delete(entity);
        }
    }
}
