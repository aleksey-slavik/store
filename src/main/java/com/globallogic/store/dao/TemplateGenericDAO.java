package com.globallogic.store.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

abstract public class TemplateGenericDAO {

    private Session session;

    private Transaction transaction;

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

    public final void processQuery() {
        openSession();

        try {
            transaction = session.beginTransaction();
            query();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            closeSession();
        }
    }

    abstract public void query();
}
