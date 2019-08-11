package com.sunny.allauth.common.cache;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * AbstractRedisManager
 *
 * @Description
 * @Author JasonLi
 * @Date 2019-08-11 21:27
 */
public abstract class AbstractRedisManager implements ICacheManager {
    private JedisConnectionFactory jedisConnectionFactory;

}
