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

    /**
     * 授权流程：subject(用户isPermitted/hasRole)->SecurityManager->Authorizer(ModularRealmAuthorizer,PerissionResolver权限解析)->Realms(AuthorizingRealm 授权realm)
     * ModularRealmAuthorizer进行多Realm匹配流程：
     * 1，首先检查对应Realm是否实现了Authorizer
     * 2，实现了Authorizer,接着调用isPermitted/hasRole接口进行匹配
     * 3，有一个Realm匹配返回true,否则返回false
     *
     * @param roleString
     * @return
     */
    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        if ("role1".equals(roleString)) {
            return Arrays.asList(new WildcardPermission("menu:*"));
        }
        return null;
    }
}
