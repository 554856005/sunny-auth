package com.sunny.allauth.shrio.cache;

import com.sunny.allauth.common.cache.ICacheManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import javax.security.auth.Destroyable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * RedisCacheManager
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
    private ICacheManager iCacheManager;
    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>();

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        log.debug("shiro redis cache manager get cache name={}", name);

        Cache cache = cacheMap.get(name);

        if (null == cache) {
            cache = new RedisCache<K,V>(name,iCacheManager);
        }
    }
}
