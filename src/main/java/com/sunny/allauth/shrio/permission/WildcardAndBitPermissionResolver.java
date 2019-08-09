package com.sunny.allauth.shrio.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * WildcardAndBitPermissionResolver
 *
 * @author lijunsong
 * @date 2019/8/7 10:26
 * @since 1.0
 */
public class WildcardAndBitPermissionResolver implements PermissionResolver {
    @Override
    public Permission resolvePermission(String permissionString) {
        if (permissionString.startsWith("+")) {
            //使用位解析权限
            return new BitPermission(permissionString);
        }
        return new WildcardPermission(permissionString);
    }
}
