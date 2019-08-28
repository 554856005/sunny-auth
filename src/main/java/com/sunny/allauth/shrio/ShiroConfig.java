package com.sunny.allauth.shrio;

import com.sunny.allauth.shrio.credentials.RetryLimitHashedCredentialsMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ShiroConfig
 *
 * @author lijunsong
 * @date 2019/8/28 16:05
 * @since 1.0
 */
@Configuration
public class ShiroConfig {
    /**
     * 启动自定义配置密码比较器
     *
     * @return
     */
    @Bean("credentialsMatcher")
    public RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher() {
        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher();
        //如果密码加密,可以打开下面配置,UserEntity中password要同一方式加密保存
        //加密算法的名称
        credentialsMatcher.setHashAlgorithmName("MD5");
        //配置加密的次数
        credentialsMatcher.setHashIterations(1024);
        //是否存储为16进制
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Bean
    public AuthRealm authRealm() {
        AuthRealm authRealm = new AuthRealm();
        //启动自定义配置密码比较器
        authRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher());
        return authRealm;
    }
}
