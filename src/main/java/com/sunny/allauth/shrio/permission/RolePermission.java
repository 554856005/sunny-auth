package com.sunny.allauth.shrio.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.Arrays;
import java.util.Collection;

/**
 * RolePermission
 * 授权方式：
 * 1，基于角色 hasRole("role1")
 * 2，基于资源 isPermitted() isPermittedAll() isPermittedAny()
 *
 * @author lijunsong
 * @date 2019/8/6 17:55
 * @since 1.0
 */
public class RolePermission implements RolePermissionResolver {

    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        if ("role1".equals(roleString)) {
            return Arrays.asList(new WildcardPermission("menu:*"));
        }
        return null;
    }
}
