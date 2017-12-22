package com.globallogic.store.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TemplateGenericDAO<T> {

    private Session session;

    private Transaction transaction;

    private QueryDAO<T> queryDAO;

    public TemplateGenericDAO(QueryDAO<T> queryDAO) {
        this.queryDAO = queryDAO;
    }

    private void openSession() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
    }

    private void closeSession() {
        session.close();
    }

    public Session getSession() {
        return session;
    }

    public final T processQuery() {
        openSession();
        T result = null;

        try {
            transaction = session.beginTransaction();
            result = queryDAO.query(session);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            closeSession();
        }

        return result;
    }
}
