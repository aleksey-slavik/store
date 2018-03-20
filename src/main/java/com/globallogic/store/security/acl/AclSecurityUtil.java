package com.globallogic.store.security.acl;

import com.globallogic.store.domain.Identifiable;
import com.globallogic.store.domain.product.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Access control list
 *
 * @author oleksii.slavik
 */
public class AclSecurityUtil {

    /**
     * mutable acl service
     */
    private MutableAclService mutableAclService;

    public void setMutableAclService(MutableAclService mutableAclService) {
        this.mutableAclService = mutableAclService;
    }

    /**
     * Get list of sids, which have access to given object
     *
     * @param identifiable object id
     * @param permission   needed permission
     * @param clazz        obhect class
     * @return list of sids, which have access to given object
     */
    public List<String> getSidList(Identifiable identifiable, Permission permission, Class clazz) {
        List<String> sids = new ArrayList<>();
        MutableAcl acl;
        ObjectIdentity oid = new ObjectIdentityImpl(clazz.getCanonicalName(), identifiable.getId());

        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            acl = (MutableAcl) mutableAclService.readAclById(oid);
            transaction.commit();
        } catch (NotFoundException e) {
            return sids;
        }


        for (AccessControlEntry entry : acl.getEntries()) {
            if (entry.getPermission().equals(permission)) {
                sids.add(((PrincipalSid) entry.getSid()).getPrincipal());
            }
        }

        return sids;
    }

    /**
     * Provide permission to current user
     *
     * @param identifiable object id
     * @param permission   granted permission
     * @param clazz        object class
     */
    public void addPermission(Identifiable identifiable, Permission permission, Class clazz) {
        addPermission(identifiable, getUsername(), permission, clazz);
    }

    /**
     * Provide permission to user with given username
     *
     * @param identifiable object id
     * @param sid          username of user
     * @param permission   granted permission
     * @param clazz        object class
     */
    public void addPermission(Identifiable identifiable, String sid, Permission permission, Class clazz) {
        MutableAcl acl;
        ObjectIdentity oid = new ObjectIdentityImpl(clazz.getCanonicalName(), identifiable.getId());
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            try {
                acl = (MutableAcl) mutableAclService.readAclById(oid);
            } catch (NotFoundException e) {
                acl = mutableAclService.createAcl(oid);
            }

            acl.insertAce(acl.getEntries().size(), permission, new PrincipalSid(sid), true);
            mutableAclService.updateAcl(acl);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    /**
     * Remove permission from user with given username
     *
     * @param identifiable object id
     * @param sid          username of user
     * @param permission   removed permission
     * @param clazz        object class
     */
    public void deletePermission(Identifiable identifiable, String sid, Permission permission, Class clazz) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            ObjectIdentity oid = new ObjectIdentityImpl(clazz.getCanonicalName(), identifiable.getId());
            MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);
            PrincipalSid recipient = new PrincipalSid(sid);

            for (int i = 0; i < acl.getEntries().size(); i++) {
                if (acl.getEntries().get(i).getSid().equals(recipient) && acl.getEntries().get(i).getPermission().equals(permission)) {
                    System.out.println(acl.getEntries().get(i));
                    acl.deleteAce(i);
                }
            }

            mutableAclService.updateAcl(acl);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    /**
     * Get username of authenticated user from security context
     *
     * @see SecurityContextHolder
     * @return username of authenticated user
     */
    private String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) auth.getPrincipal()).getUsername();
        } else {
            return auth.getPrincipal().toString();
        }
    }
}
