package com.globallogic.store.dao;

import com.globallogic.store.model.Product;
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

/**
 * CRUD operations related with product table.
 *
 * @author oleksii.slavik
 */
public class ProductDAO /*implements GenericDAO<Product, Long>*/ {

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
     * Return list of all products
     *
     * @return list of all products
     */
    public List<Product> findAll() {
        openSession();
        List<Product> products = null;

        try {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root);
            Query<Product> q = session.createQuery(query);
            products = q.getResultList();
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

        return products;
    }

    /**
     * Return product by given id
     *
     * @param id given id
     * @return product by given id
     */
    public Product findById(Long id) {
        openSession();
        Product product = null;

        try {
            transaction = session.beginTransaction();
            product = session.get(Product.class, id);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            closeSession();
        }

        return product;
    }

    /**
     * Add given product to database
     *
     * @param entity given product
     */
    public Long create(Product entity) {
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
     * Update given product data
     *
     * @param entity given product data
     */
    public void update(Product entity) {
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
     * Delete given product from database
     *
     * @param entity given product
     */
    public void delete(Product entity) {
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
