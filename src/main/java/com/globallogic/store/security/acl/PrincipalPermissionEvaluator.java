package com.globallogic.store.security.acl;

import com.globallogic.store.domain.Identifiable;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class PrincipalPermissionEvaluator implements PermissionEvaluator {

    private PermissionService permissionService;

    public PrincipalPermissionEvaluator(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if ((authentication == null) || !(targetDomainObject instanceof Identifiable) || !(permission instanceof String)) {
            return false;
        } else {
            return permissionService.hasPrivilege(((Identifiable)targetDomainObject).getId(), targetDomainObject.getClass().getCanonicalName(), (String) permission);
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if ((authentication == null) || !(targetId instanceof Long) || (targetType == null) || !(permission instanceof String)) {
            return false;
        } else {
            return permissionService.hasPrivilege((Long) targetId, targetType, (String) permission);
        }
    }
}
