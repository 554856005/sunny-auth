package com.sunny.allauth.common.cache;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisManager
 *
 * @Description
 * @Author JasonLi
 * @Date 2019-08-11 21:25
 */
public class RedisManager implements IRedisManager {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void expire(String key, long time) {
        //设置失效时间单位为秒
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    @Override
    public void set(String key, Object value, long expire) {
        if (expire > 0) {
            redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
        } else {
            //time要大于0 如果time小于等于0 将设置无限期
            redisTemplate.opsForValue().set(key, value);
        }
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void del(String... key) {
        if (ArrayUtil.isNotEmpty(key)) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    @Override
    public void del(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    @Override
    public Set<String> scan(String preKey) {
        Set<String> keys = (Set<String>) redisTemplate.execute((RedisCallback) connection -> {
            Set<String> binaryKeys = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(preKey).count(1000).build());
            while (cursor.hasNext()) {
                binaryKeys.add(new String(cursor.next()));
            }
            return binaryKeys;
        });
        return keys;
    }

    @Override
    public Long scanSize(String preKey) {
        long dbSize = (long) redisTemplate.execute((RedisCallback) connection -> {
            long count = 0L;
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(preKey).count(1000).build());
            while (cursor.hasNext()) {
                cursor.next();
                count++;
            }
            return count;
        });
        return dbSize;
    }

    @Override
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
