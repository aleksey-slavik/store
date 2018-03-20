package com.globallogic.store.security.core;

import com.globallogic.store.domain.Identifiable;
import com.globallogic.store.security.service.PermissionService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * Custom permission evaluator
 *
 * @author oleksii.slavik
 */
public class PrincipalPermissionEvaluator implements PermissionEvaluator {

    /**
     * permission service
     */
    private PermissionService permissionService;

    public PrincipalPermissionEvaluator(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * Check have current user permission to access to object
     *
     * @param authentication     authentication data
     * @param targetDomainObject domain object
     * @param permission         needed permission
     * @return true, if user have permission to access domain object, false in otherwise
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if ((authentication == null) || !(targetDomainObject instanceof Identifiable) || !(permission instanceof String)) {
            return false;
        } else {
            return permissionService.hasPrivilege(((Identifiable) targetDomainObject).getId(), targetDomainObject.getClass().getCanonicalName(), (String) permission);
        }
    }

    /**
     * Check have current user permission to access to object
     *
     * @param authentication authentication data
     * @param targetId       object id
     * @param targetType     object type
     * @param permission     needed permission
     * @return true, if user have permission to access domain object, false in otherwise
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if ((authentication == null) || !(targetId instanceof Long) || (targetType == null) || !(permission instanceof String)) {
            return false;
        } else {
            return permissionService.hasPrivilege((Long) targetId, targetType, (String) permission);
        }
    }
}
