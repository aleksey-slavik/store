package com.globallogic.store.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AbstractGenericDAO<T> implements GenericDAO<T> {

    private Class<T> type;

    private Session session;

    private Transaction transaction;

    public AbstractGenericDAO(Class<T> type) {
        this.type = type;
    }

    private void openSession() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
    }

    private void closeSession() {
        session.close();
    }

    public List<T> findAll() {
        openSession();
        List<T> list = null;

        try {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);
            query.select(root);
            Query<T> q = session.createQuery(query);
            list = q.getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } catch (NoResultException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            closeSession();
        }

        return list;
    }

    public T findById(Long id) {
        openSession();
        T item = null;

        try {
            transaction = session.beginTransaction();
            item = session.get(type, id);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            closeSession();
        }

        return item;
    }

    public Long create(T entity) {
        openSession();
        Long id = null;

        try {
            transaction = session.beginTransaction();
            id = (Long) session.save(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            closeSession();
        }

        return id;
    }

    public void update(T entity) {
        openSession();

        try {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        } finally {
            closeSession();
        }
    }

    public void delete(T entity) {
        openSession();

        try {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            closeSession();
        }
    }
}
