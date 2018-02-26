package com.globallogic.store.security.acl;

import com.globallogic.store.domain.user.PermissionName;
import com.globallogic.store.security.AuthenticatedUser;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class AccessPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        boolean hasPermission = false;

        if (authentication != null && authentication.getPrincipal() instanceof AuthenticatedUser && permission instanceof String) {
            AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

            if (user.getPermissions().contains(PermissionName.valueOf(permission.toString()))) {
                hasPermission = true;
            }
        }

        return hasPermission;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
