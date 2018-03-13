package com.globallogic.store.dao.query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Consist logic of the query to the database.
 *
 * @param <T> type of data
 * @author oleksii.slavik
 */
public class GenericQuery<T> {

    /**
     * Process of given query.
     *
     * @param queryable given query
     * @return result of query
     */
    public final T processQuery(Queryable<T> queryable) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Transaction transaction = null;
        T result = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            result = queryable.query(session);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return result;
    }
}
