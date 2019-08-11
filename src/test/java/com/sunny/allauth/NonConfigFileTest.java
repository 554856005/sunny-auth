package com.sunny.allauth;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;


/**
 * NonConfigFileTest
 *
 * @Description
 * @Author JasonLi
 * @Date 2019-08-08 22:51
 */
public class NonConfigFileTest {
    /**
     * Shiro是从根对象SecurityManager进行身份认证和授权的，这个对象是线程安全并且整个应用只需要
     * 一个即可，因此Shiro提供了SecurityUtils可以将它设置为全局。
     * Shiro中的类都是POJO，它们可以很容易放到任何IoC容器管理中。但和一般的IoC不同之处，shiro是从
     * 根对象SecurityManager开始，Shiro支持的依赖注入：无参数构造方法，setter。
     */
    @Test
    public void Test() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        //authenticator设置
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);

        //authorizer设置
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        securityManager.setAuthorizer(authorizer);

        //Realm设置
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.driver");
        dataSource.setUrl("jdbc:mysql:/localhost:3306/shrio");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);
        securityManager.setRealm(jdbcRealm);

        //SecurityManager设置到SecurityUtils 方便全局使用
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("JasonLi", "11111");
        subject.login(token);

        Assert.assertTrue(subject.isAuthenticated());
    }
}
