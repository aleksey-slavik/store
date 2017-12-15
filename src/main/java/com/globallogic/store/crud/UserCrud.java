package com.globallogic.store.crud;

import com.globallogic.store.exception.SameUserFoundException;
import com.globallogic.store.field.Table;
import com.globallogic.store.model.Login;
import com.globallogic.store.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;

/**
 * CRUD operations related with users table.
 *
 * @author oleksii.slavik
 */
class UserCrud {

    /**
     * Add new user if it already not exist in database
     *
     * @param user user data
     * @return id of new record in database
     */
    public static Long registerUser(User user) throws SameUserFoundException {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Long id = null;

        try {
            transaction = session.beginTransaction();

            if (isUserAlreadyExist(user)) {
                throw new SameUserFoundException();
            }

            id = (Long) session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return id;
    }

    /**
     * Check that logined user already exist in database
     *
     * @param login login data
     * @return user data
     */
    public static User verifyUser(Login login) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        User user = null;

        try {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root);

            query.where(builder.and(
                    builder.equal(root.get(Table.User.USERNAME), login.getUsername()),
                    builder.equal(root.get(Table.User.PASSWORD), login.getPassword())
            ));

            Query<User> q = session.createQuery(query);
            user = q.getSingleResult();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return user;
    }

    /**
     * Update given user data
     *
     * @param id        id
     * @param firstname firstname
     * @param lastname  lastname
     * @param username  username
     * @param password  password
     * @param email     email
     */
    public static void updateUser(
            Long id,
            String firstname,
            String lastname,
            String username,
            String password,
            String email) {

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            session.update(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Delete given user account from database.
     *
     * @param id user id
     */
    public static void deleteUser(Long id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Check exist or not given user in database
     *
     * @param user given user data
     * @return true if given user exist in database, false otherwise
     */
    private static boolean isUserAlreadyExist(User user) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        User result = null;

        try {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root);
            query.where(builder.equal(root.get(Table.User.USERNAME), user.getUsername()));
            Query<User> q = session.createQuery(query);
            result = q.getSingleResult();
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
            session.close();
        }

        return result != null;
    }
}
