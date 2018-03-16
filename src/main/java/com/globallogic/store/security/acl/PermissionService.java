package com.globallogic.store.security.acl;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.criteria.SearchCriteria;
import com.globallogic.store.domain.Identifiable;
import com.globallogic.store.domain.permission.Permission;
import com.globallogic.store.domain.permission.PermissionName;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class PermissionService {

    /**
     * permission DAO
     */
    private GenericDao<Permission> permissionDao;

    public PermissionService(GenericDao<Permission> permissionDao) {
        this.permissionDao = permissionDao;
    }

    public boolean hasPrivilege(long id, String clazz, String permission) {
        try {
            SearchCriteria criteria = new SearchCriteria()
                    .criteria("objectClass", clazz)
                    .criteria("objectId", id)
                    .criteria("sid", getPrincipal())
                    .criteria("permission", PermissionName.valueOf(permission));

            Permission privilege = permissionDao.getEntity(criteria);
            System.out.println(privilege);
            return privilege != null;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getPrincipalList(Identifiable identifiable, PermissionName permission, Class clazz) {
        SearchCriteria criteria = new SearchCriteria()
                .criteria("object_class", clazz.getCanonicalName())
                .criteria("object_id", identifiable.getId())
                .criteria("permission", permission)
                .offset(1)
                .limit(100)
                .sortBy("id")
                .order("asc");

        List<String> principals = new ArrayList<>();

        for (Permission granted : permissionDao.getEntityList(criteria)) {
            principals.add(granted.getSid());
        }

        return principals;
    }

    public void addPermission(Identifiable identifiable, PermissionName permission, Class clazz) {
        addPermission(identifiable, getPrincipal(), permission, clazz);
    }

    public void addPermission(Identifiable identifiable, String principal, PermissionName permission, Class clazz) {
        Permission granted = new Permission();
        granted.setObjectClass(clazz.getCanonicalName());
        granted.setObjectId(identifiable.getId());
        granted.setSid(principal);
        granted.setPermission(permission);
        permissionDao.createEntity(granted);
    }

    public void deletePermission(Identifiable identifiable, String principal, PermissionName permission, Class clazz) {
        SearchCriteria criteria = new SearchCriteria()
                .criteria("object_class", clazz.getCanonicalName())
                .criteria("object_id", identifiable.getId())
                .criteria("sid", principal)
                .criteria("permission", permission);

        Permission deleted = permissionDao.getEntity(criteria);
        permissionDao.deleteEntityByKey(deleted.getId());
    }

    private String getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) auth.getPrincipal()).getUsername();
        } else {
            return auth.getPrincipal().toString();
        }
    }
}
