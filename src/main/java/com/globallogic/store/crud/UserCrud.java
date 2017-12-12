package com.globallogic.store.crud;

import com.globallogic.store.model.Login;
import com.globallogic.store.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * CRUD operations related with users table.
 *
 * @author oleksii.slavik
 */
class UserCrud {

    /**
     * Return list of all users
     *
     * @return list of all users
     */
    public static List<User> getUserList() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        Query<User> q = session.createQuery(query);
        List<User> users = q.getResultList();

        session.close();
        return users;
    }

    /**
     * Add new user if it already not exist in database
     *
     * @param user user data
     */
    public static void registerUser(User user) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        //todo check that user already registered

        session.save(user);
        transaction.commit();

        session.close();
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
        session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);

        query.where(builder.and(
                builder.equal(root.get("username"), login.getUsername()),
                builder.equal(root.get("password"), login.getPassword())
        ));

        Query<User> q = session.createQuery(query);
        User user = q.getSingleResult();

        session.close();
        return user;
    }

    /**
     * Update given user data
     *
     * @param user given user data
     */
    public static void updateUser(User user) {//need full object
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.update(user);
        transaction.commit();

        session.close();
    }

    /**
     * Delete given user account from database
     *
     * @param user given user
     */
    public static void deleteUser(User user) {//need full object
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.delete(user);
        transaction.commit();

        session.close();
    }
}
