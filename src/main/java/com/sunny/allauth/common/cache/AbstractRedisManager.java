package com.sunny.allauth.common.cache;

/**
 * AbstractRedisManager
 *
 * @Description
 * @Author JasonLi
 * @Date 2019-08-11 21:27
 */
public abstract class AbstractRedisManager implements ICacheManager {
    private org.springframework.data.redis.connection.jedis.JedisConnectionFactory jedisConnectionFactory;

}
