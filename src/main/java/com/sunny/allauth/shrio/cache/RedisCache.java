package com.sunny.allauth.shrio.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sunny.allauth.common.Consts.SHIRO_REDIS_CACHE_KEY_PREFIX;

/**
 * RedisCache
 *
 * @Description
 * @Author JasonLi
 * @Date 2019-08-11 14:00
 */
@Slf4j
public class RedisCache<K, V> implements Cache<K, V> {
    /**
     * 缓存名称
     */
    private String name;
    /**
     * 存储key的redis.list的key值
     */
    private String keyListKey;
    /**
     * jedis 操作接口
     */
    private JedisConnectionFactory iCacheManager;
    /**
     * 序列号工具
     */
    private RedisSerializer serializer = new JdkSerializationRedisSerializer();

    public RedisCache(String name, JedisConnectionFactory iCacheManager) {
        this.name = name;
        this.iCacheManager = iCacheManager;
        this.keyListKey = String.format("%s%s", SHIRO_REDIS_CACHE_KEY_PREFIX, name);
    }

    /**
     * 重组Key
     * 区别其他使用环境的Key
     *
     * @param key
     * @return
     */
    private String genKey(K key) {
        return String.format("%s%s_%s", SHIRO_REDIS_CACHE_KEY_PREFIX, name, key);
    }

    @Override
    public V get(K k) throws CacheException {
        log.debug("shiro redis cache get.{} k={}", name, k);
        V result = null;
        RedisConnection connection = null;
        try {
            connection = iCacheManager.getConnection();
            result = (V) serializer.deserialize(connection.get(serializer.serialize(genKey(k))));
        } catch (Exception e) {
            log.error("shiro redis cache get exception:", e);
        }
        return result;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        log.debug("shiro redis cache put.{} K={},V={}", name, k, v);
        V result = null;
        RedisConnection connection = null;
        try {
            connection = iCacheManager.getConnection();
            result = (V) serializer.deserialize(connection.get(serializer.serialize(genKey(k))));
            //存放value
            connection.set(serializer.serialize(genKey(k)), serializer.serialize(v));
            //记录键值中List
            connection.lPush(serializer.serialize(keyListKey), serializer.serialize(genKey(k)));
        } catch (Exception e) {
            log.error("shiro redis cache put exception:", e);
        }
        return result;
    }

    @Override
    public V remove(K k) throws CacheException {
        log.debug("shiro redis cache remove.{} K={}", name, k);
        V result = null;
        RedisConnection connection = null;
        try {
            connection = iCacheManager.getConnection();
            result = (V) serializer.deserialize(connection.get(serializer.serialize(genKey(k))));

            //设置失效
            connection.expireAt(serializer.serialize(genKey(k)), 0);
            connection.lRem(serializer.serialize(keyListKey), 0, serializer.serialize(k));
        } catch (Exception e) {
            log.error("shiro redis cache remove exception:", e);
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
        return result;
    }

    @Override
    public void clear() throws CacheException {
        log.debug("shiro redis cache clear.{} ", name);
        RedisConnection connection = null;
        try {
            connection = iCacheManager.getConnection();

            Long length = connection.lLen(serializer.serialize(keyListKey));
            if (0 == length) {
                return;
            }
            List<byte[]> keyList = connection.lRange(serializer.serialize(keyListKey), 0, length - 1);
            for (byte[] key : keyList) {
                connection.expireAt(key, 0);
            }
            connection.expireAt(serializer.serialize(keyListKey), 0);
            keyList.clear();
        } catch (Exception e) {
            log.error("shiro redis cache clear exception:", e);
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
    }

    @Override
    public int size() {
        log.debug("shiro redis cache size.{}", name);
        int result = 0;
        RedisConnection connection = null;
        try {
            connection = iCacheManager.getConnection();
            result = Math.toIntExact(connection.lLen(serializer.serialize(keyListKey)));
        } catch (Exception e) {
            log.error("shiro redis cache size exception:", e);
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
        return result;
    }

    @Override
    public Set<K> keys() {
        log.debug("shiro redis cache keys.{}", name);
        Set result = null;
        RedisConnection connection = null;
        try {
            connection = iCacheManager.getConnection();
            Long length = connection.lLen(serializer.serialize(keyListKey));
            if (0 == length) {
                return null;
            }
            List<byte[]> keyList = connection.lRange(serializer.serialize(keyListKey), 0, length - 1);
            result = keyList.stream().map(bytes -> serializer.deserialize(bytes)).collect(Collectors.toSet());

        } catch (Exception e) {
            log.error("shiro redis cache size exception:", e);
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
        return result;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
