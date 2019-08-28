package com.sunny.allauth.shrio;

import com.sunny.allauth.shrio.credentials.RetryLimitHashedCredentialsMatcher;
import com.sunny.allauth.shrio.session.RedisCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ShiroConfig
 *
 * @author lijunsong
 * @date 2019/8/28 16:05
 * @since 1.0
 */
@Slf4j
@Configuration
public class ShiroConfig {
    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm,
                                           @Qualifier("cookieRememberMeManager") CookieRememberMeManager rememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //启动自定义Realm
        securityManager.setRealm(authRealm);
        //启动rememberMe管理器
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    @Bean
    public AuthRealm authRealm() {
        AuthRealm authRealm = new AuthRealm();
        //启动自定义配置密码比较器
        authRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher());
        //启动自定义缓存管理器
        authRealm.setCacheManager(redisCacheManager());
        return authRealm;
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        log.info("rememberMeCookie");
        //前端对应记住选择框name="rememberMe"
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        //记住一天，单位秒
        cookie.setMaxAge(24 * 60 * 60);
        return cookie;
    }

    /**
     * rememberMe管理器
     */
    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        log.info("rememberMeManager");
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(rememberMeCookie());
        return rememberMeManager;
    }

    /**
     * 自定义配置密码比较器
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

    /**
     * 开启Shiro AOP，这样才能使用@RequiresPermissions
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager) {
        log.info("authorizationAttributeSourceAdvisor");
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 拦截过滤
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        log.info("ShiroFilterFactoryBean");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器
        Map<String, String> map = new LinkedHashMap<>();

        //如果不设置，默认找Web工程根目录下的“/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功，跳转url
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
}
