package com.globallogic.store.security.service;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.criteria.SearchCriteria;
import com.globallogic.store.domain.Identifiable;
import com.globallogic.store.domain.permission.Permission;
import com.globallogic.store.domain.permission.PermissionName;
import com.globallogic.store.exception.AlreadyExistException;
import com.globallogic.store.exception.NoContentException;
import com.globallogic.store.exception.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Permission service
 *
 * @author oleksii.slavik
 */
public class PermissionService {

    /**
     * permission DAO
     */
    private GenericDao<Permission> permissionDao;

    public PermissionService(GenericDao<Permission> permissionDao) {
        this.permissionDao = permissionDao;
    }

    /**
     * Check permission of current user
     *
     * @param id         object id
     * @param clazz      object class
     * @param permission needed permission
     * @return true, if user have permission to access object, false in otherwise
     */
    public boolean hasPrivilege(long id, String clazz, String permission) {
        try {
            checkPermission(id, getPrincipal(), PermissionName.valueOf(permission), clazz);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    /**
     * Get list of username of users, which have access to given object
     *
     * @param identifiable given object
     * @param permission   needed permission
     * @param clazz        object class
     * @return list of username of users with needed permission
     * @throws NoContentException throws when user list with needed permissions is empty
     */
    public List<String> getPrincipalList(Identifiable identifiable, PermissionName permission, Class clazz) throws NoContentException {
        SearchCriteria criteria = new SearchCriteria()
                .criteria("objectClass", clazz.getCanonicalName())
                .criteria("objectId", identifiable.getId())
                .criteria("permission", permission)
                .offset(1)
                .limit(100)
                .sortBy("id")
                .order("asc");

        List<Permission> permissions = permissionDao.getEntityList(criteria);

        if (permissions == null || permissions.isEmpty()) {
            throw new NoContentException("There are no user with " + permission + " permission found!");
        } else {
            List<String> principals = new ArrayList<>();

            for (Permission granted : permissionDao.getEntityList(criteria)) {
                principals.add(granted.getSid());
            }

            return principals;
        }
    }

    /**
     * Get list of id of object, to which user with given username have access
     *
     * @param principal  username of user
     * @param permission needed permission
     * @param clazz      object class
     * @return list of id of object, to which user with given username have access
     * @throws NoContentException throws when available object list for given user is empty
     */
    public List<Long> getIdList(String principal, PermissionName permission, Class clazz) throws NoContentException {
        SearchCriteria criteria = new SearchCriteria()
                .criteria("sid", principal)
                .criteria("objectClass", clazz.getCanonicalName())
                .criteria("permission", permission)
                .offset(1)
                .limit(100)
                .sortBy("id")
                .order("asc");

        List<Permission> permissions = permissionDao.getEntityList(criteria);

        if (permissions == null || permissions.isEmpty()) {
            throw new NoContentException("There are no granted " + permission + " permission for " + principal);
        } else {
            List<Long> ids = new ArrayList<>();

            for (Permission granted : permissions) {
                ids.add(granted.getObjectId());
            }

            return ids;
        }
    }

    /**
     * Provide permission to current user
     *
     * @param identifiable object id
     * @param permission   granted permission
     * @param clazz        object class
     * @throws AlreadyExistException throws when permission already exist in database
     */
    public void addPermission(Identifiable identifiable, PermissionName permission, Class clazz) throws AlreadyExistException {
        addPermission(identifiable, getPrincipal(), permission, clazz);
    }

    /**
     * Provide permission to user with given username
     *
     * @param identifiable object id
     * @param principal    username of user
     * @param permission   granted permission
     * @param clazz        object class
     * @throws AlreadyExistException throws when permission already exist in database
     */
    public void addPermission(Identifiable identifiable, String principal, PermissionName permission, Class clazz) throws AlreadyExistException {
        try {
            checkPermission(identifiable.getId(), principal, permission, clazz.getCanonicalName());
            throw new AlreadyExistException(permission + " permission already exist for " + principal);
        } catch (NotFoundException e) {
            Permission granted = new Permission();
            granted.setObjectClass(clazz.getCanonicalName());
            granted.setObjectId(identifiable.getId());
            granted.setSid(principal);
            granted.setPermission(permission);
            permissionDao.createEntity(granted);
        }
    }

    /**
     * Remove permission from user with given username
     *
     * @param identifiable object id
     * @param principal    username of user
     * @param permission   removed permission
     * @param clazz        object class
     * @throws NotFoundException throws when permission not exist
     */
    public void deletePermission(Identifiable identifiable, String principal, PermissionName permission, Class clazz) throws NotFoundException {
        Permission deleted = checkPermission(identifiable.getId(), principal, permission, clazz.getCanonicalName());
        permissionDao.deleteEntity(deleted);
    }

    /**
     * Remove all user permissions for given object
     *
     * @param identifiable object id
     * @param clazz        object class
     */
    public void deletePermission(Identifiable identifiable, Class clazz) {
        SearchCriteria criteria = new SearchCriteria()
                .criteria("objectClass", clazz.getCanonicalName())
                .criteria("objectId", identifiable.getId())
                .offset(1)
                .limit(100)
                .sortBy("id")
                .order("asc");

        List<Permission> deleted = permissionDao.getEntityList(criteria);

        if (deleted != null && !deleted.isEmpty()) {
            for (Permission permission : deleted) {
                permissionDao.deleteEntity(permission);
            }
        }
    }

    /**
     * Get username of authenticated user from security context
     *
     * @return username of authenticated user
     * @see SecurityContextHolder
     */
    private String getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) auth.getPrincipal()).getUsername();
        } else {
            return auth.getPrincipal().toString();
        }
    }

    /**
     * Check availability of permission in database
     *
     * @param id         object id
     * @param principal  username of user
     * @param permission removed permission
     * @param clazz      object class
     * @return available permission
     * @throws NotFoundException throws when permission not exist
     */
    private Permission checkPermission(long id, String principal, PermissionName permission, String clazz) throws NotFoundException {
        SearchCriteria criteria = new SearchCriteria()
                .criteria("objectClass", clazz)
                .criteria("objectId", id)
                .criteria("sid", principal)
                .criteria("permission", permission);

        Permission checked = permissionDao.getEntity(criteria);

        if (checked == null) {
            throw new NotFoundException(permission + " permission not found for " + principal);
        } else {
            return checked;
        }
    }
}
