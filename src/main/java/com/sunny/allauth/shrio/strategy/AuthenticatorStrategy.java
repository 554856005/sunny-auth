package com.sunny.allauth.shrio.strategy;

import cn.hutool.core.util.ArrayUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AbstractAuthenticationStrategy;

/**
 * AuthenticatorStrategy
 * 认证策略：
 * 1，FirstSuccessfulStrategy,只要有一个Realm验证成功即可，只返回第一个验证成功认证信息
 * 2，AtLeastOneSucessfulStrategy,只要有个Realm验证成功，返回所有验证成功的认证信息
 * 3，AllSuceessfulStrategy,所有Realm验证成功才能算成功，返回所有验证成功的认证信息
 *
 * @author lijunsong
 * @date 2019/8/6 17:26
 * @since 1.0
 */
public class AuthenticatorStrategy extends AbstractAuthenticationStrategy {
    /**
     * 认证流程：subject(用户登录）->SecurityManager->Authenticator->AuthenticatorStrategy->Realms(JDBC Relam,LDAP Realm,ActiveDirectory Realm,Custom Realm)
     *
     * @param token
     * @param aggregate
     * @return
     */
    @Override
    public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate) {
        //至少两个realm验证成功
        if (aggregate == null || ArrayUtil.isEmpty(aggregate.getPrincipals()) || aggregate.getPrincipals().getRealmNames().size() < 2) {
            throw new AuthenticationException("Authentication token of type [" + token.getClass() + "] " +
                    "could not be authenticated by any configured realms.  Please ensure that at least two realm can " +
                    "authenticate these tokens.");
        }
        return aggregate;
    }
}
