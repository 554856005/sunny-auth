package com.sunny.allauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RedisTestController
 *
 * @author lijunsong
 * @date 2019/8/14 14:20
 * @since 1.0
 */
@RestController
public class RedisTestController {
    /**
     * Spring Data Redis 提供两个模板：
     * RedisTemplate
     * StringRedisTemplate 只关注String类型
     */
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @RequestMapping("redis/get")
    public String get() {
        stringRedisTemplate.opsForValue().set("key", "我支持香港警察！");
        return stringRedisTemplate.opsForValue().get("key");
    }

    @RequestMapping("redis/find")
    public String find() {
        redisTemplate.opsForValue().set("redis", "你可以打我");
        //要转成string
        return (String) redisTemplate.opsForValue().get("redis");
    }
}
