package com.sunny.allauth.shrio.credentials;

import com.sunny.allauth.enums.ActiveEnum;
import com.sunny.allauth.service.UserService;
import com.sunny.allauth.shrio.session.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

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
@Slf4j
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    @Autowired
    UserService userService;
    private RedisCache redisCache;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String userName = (String) token.getPrincipal();
        String redisKey = SHIRO_REDIS_CACHE_KEY_PREFIX + userName;
        AtomicInteger retryCount = (AtomicInteger) redisCache.get(redisKey);
        if (retryCount == null) {
            /**
             * AtomicInteger中的部分源码，在这里说下其中的value，这里value使用了volatile关键字,
             * volatile在这里可以做到的作用是使得多个线程可以共享变量，
             * 但是问题在于使用volatile将使得VM优化失去作用，导致效率较低，所以要在必要的时候使用，
             * 因此AtomicInteger类不要随意使用，要在使用场景下使用
             */
            retryCount = new AtomicInteger(0);
            redisCache.put(redisKey, retryCount);
        }
        if (retryCount.incrementAndGet() > 5) {
            log.info("账户或密码错误5次，禁用用户[{}]", userName);
            userService.updateStatus(userName, ActiveEnum.DISABLE);
            throw new ExcessiveAttemptsException("账户或密码错误5次，请一小时之后再尝试");
        }
        boolean match = super.doCredentialsMatch(token, info);
        if (match) {
            //登录成功，置空该用户登录计数
            redisCache.remove(redisKey);
        }
        return match;
    }

    /**
     * @param userName
     */
    public void unlockUser(String userName) {
        userService.updateStatus(userName, ActiveEnum.ENABLED);
        redisCache.remove(SHIRO_REDIS_CACHE_KEY_PREFIX + userName);
    }
}
