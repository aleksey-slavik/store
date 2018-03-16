package com.globallogic.store.security.acl;

import com.globallogic.store.domain.Identifiable;
import com.globallogic.store.domain.product.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class AclSecurityUtil {

    private MutableAclService mutableAclService;

    public void setMutableAclService(MutableAclService mutableAclService) {
        this.mutableAclService = mutableAclService;
    }

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
                sids.add(((PrincipalSid)entry.getSid()).getPrincipal());
            }
        }

        return sids;
    }

    public void addPermission(Identifiable identifiable, Permission permission, Class clazz) {
        addPermission(identifiable, getUsername(), permission, clazz);
    }

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

    public void deletePermission(Identifiable identifiable, String sid, Permission permission, Class clazz) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            ObjectIdentity oid = new ObjectIdentityImpl(clazz.getCanonicalName(), identifiable.getId());
            MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);
            List<AccessControlEntry> entries = acl.getEntries();
            PrincipalSid recipient = new PrincipalSid(sid);

            for (int i = 0; i < entries.size(); ) {
                if (entries.get(i).getSid().equals(recipient) && entries.get(i).getPermission().equals(permission)) {
                    acl.deleteAce(i);
                    entries = acl.getEntries();
                    i = 0;
                    continue;
                }

                i++;
            }

            mutableAclService.updateAcl(acl);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    private String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) auth.getPrincipal()).getUsername();
        } else {
            return auth.getPrincipal().toString();
        }
    }
}
