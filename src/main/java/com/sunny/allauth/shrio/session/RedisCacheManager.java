package com.sunny.allauth.shrio.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import javax.security.auth.Destroyable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * RedisCacheManager
 * 使用Redis作为缓存需要shiro重写cache、cacheManager、SessionDAO
 *
 * @Description
 * @Author JasonLi
 * @Date 2019-08-11 13:54
 */
@Slf4j
public class RedisCacheManager implements CacheManager, Destroyable {
    /**
     * 缓存操作接口
     */
    private JedisConnectionFactory iCacheManager;
    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>();

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        log.debug("shiro redis session manager get session name={}", name);

        Cache cache = cacheMap.get(name);

        if (null == cache) {
            cache = new RedisCache<K, V>(name, iCacheManager);
        }
        return cache;
    }
}
