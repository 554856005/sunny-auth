package com.sunny.allauth.shrio.credentials;

import com.sunny.allauth.shrio.cache.RedisCache;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import javax.xml.bind.Element;

import static com.sunny.allauth.common.Consts.SHIRO_REDIS_CACHE_KEY_PREFIX;

/**
 * RetryLimitHashedCredentialsMatcher
 * 密码重试次数限制
 * 1小时最多错误5次，超过5次锁定1小时，一小时之后再可以尝试，如果还是错误，锁定一天
 * 防止账号被暴力破解
 *
 * @Description
 * @Author JasonLi
 * @Date 2019-08-11 13:41
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    private RedisCache redisCache;

    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String userName = (String) token.getPrincipal();
        Element element = redisCache.get(SHIRO_REDIS_CACHE_KEY_PREFIX);
    }
}
