package com.globallogic.store.dao;

import com.globallogic.store.field.Table;
import com.globallogic.store.model.Login;
import com.globallogic.store.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * CRUD operations related with users table.
 *
 * @author oleksii.slavik
 */
public class UserDAO implements UserDAOInterface<User, Long, Login> {

    private Session session;

    private Transaction transaction;

    private void openSession() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
    }

    private void closeSession() {
        session.close();
    }

    /**
     * Check that logined user already exist in database
     *
     * @param item login data
     * @return user data
     */
    public User verify(Login item) {
        openSession();
        User user = null;

        try {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root);

            query.where(builder.and(
                    builder.equal(root.get(Table.User.USERNAME), item.getUsername()),
                    builder.equal(root.get(Table.User.PASSWORD), item.getPassword())
            ));

            Query<User> q = session.createQuery(query);
            user = q.getSingleResult();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            closeSession();
        }

        return user;
    }

    public List<User> findAll() {
        return null;
    }

    public User findById(Long aLong) {
        return null;
    }

    /**
     * Add new user if it already not exist in database
     *
     * @param entity user data
     * @return id of new record in database
     */
    public Long create(User entity) {
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

    /**
     * Update given user data
     *
     * @param entity given user data
     */
    public void update(User entity) {
        openSession();

        try {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            closeSession();
        }
    }

    /**
     * Delete given user account from database.
     *
     * @param entity given user
     */
    public void delete(User entity) {
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
