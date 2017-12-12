package com.globallogic.store.crud;

import com.globallogic.store.exception.ProductNotFoundException;
import com.globallogic.store.model.Product;
import org.hibernate.HibernateException;
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
 * CRUD operations related with product table.
 *
 * @author oleksii.slavik
 */
class ProductCrud {

    /**
     * Return list of all products
     *
     * @return list of all products
     */
    public static List<Product> getProductList() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Product> products = null;

        try {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root);
            Query<Product> q = session.createQuery(query);
            products = q.getResultList();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        } finally {
            session.close();
        }

        return products;
    }

    /**
     * Return product by given id
     *
     * @param id given id
     * @return product by given id
     * @throws ProductNotFoundException thrown when product with given id not exist in database
     */
    public static Product getProductById(Long id) throws ProductNotFoundException {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Product product = null;

        try {
            transaction = session.beginTransaction();
            product = session.get(Product.class, id);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        } finally {
            session.close();
        }

        if (product != null) {
            return product;
        } else {
            throw new ProductNotFoundException();
        }
    }

    /**
     * Update given product data
     *
     * @param id          id
     * @param name        name
     * @param brand       brand
     * @param description description
     * @param price       price
     */
    public static void updateProduct(
            Long id,
            String name,
            String brand,
            String description,
            Double price) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            product.setName(name);
            product.setBrand(brand);
            product.setDescription(description);
            product.setPrice(price);
            session.update(product);
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
     * Add given product to database
     *
     * @param product given product
     */
    public static Long createProduct(Product product) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Long id = null;

        try {
            transaction = session.beginTransaction();
            id = (Long) session.save(product);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        } finally {
            session.close();
        }

        return id;
    }

    /**
     * Delete given product from database
     *
     * @param id product id
     */
    public static void deleteProduct(Long id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.delete(product);
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
}
