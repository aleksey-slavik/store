package com.globallogic.store.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Implementation of {@link Queryable} interface.
 * Consist logic of the query to the database.
 *
 * @param <T> type of data
 * @author oleksii.slavik
 */
public class TemplateGenericDAO<T> {

    /**
     * Process of given query.
     *
     * @param queryable given query
     * @return result of query
     */
    public final T processQuery(Queryable<T> queryable) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        T result = null;

        try {
            transaction = session.beginTransaction();
            result = queryable.query(session);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return result;
    }
}
