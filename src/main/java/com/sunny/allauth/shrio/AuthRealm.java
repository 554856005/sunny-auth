package com.sunny.allauth.shrio;

import com.sunny.allauth.entity.UserEntity;
import com.sunny.allauth.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * AuthRealm
 *
 * @author lijunsong
 * @date 2019/8/28 13:32
 * @since 1.0
 */
public class AuthRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //拥有的角色 方法使用@RequiresPermissions注解可判断拥有该角色才能访问
        authorizationInfo.setRoles(userService.findRoles(userName));
        //拥有的权限 方法使用@RequiresPermissions注解可判断拥有该权限才能访问
        authorizationInfo.setStringPermissions(userService.findPermissions(userName));
        return authorizationInfo;
    }

    /**
     * 身份认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        UserEntity user = userService.findByUserName(userName);
        if (user == null) {
            throw new UnknownAccountException("用户名称或密码错误");
        }
        if (user.isNotActive()) {
            throw new LockedAccountException("该账户没激活");
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUserName(),
                user.getPassword(),
                //加盐 HashedCredentialsMatcher自动识别该盐
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );
        return authenticationInfo;
    }
}
