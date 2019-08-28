package com.sunny.allauth.common.cache;

import java.util.Collection;
import java.util.Set;

/**
 * IRedisManager
 *
 * @Description
 * @Author JasonLi
 * @Date 2019-08-11 20:54
 */
public interface IRedisManager {
    /**
     * 设置缓存失效时间
     *
     * @param key
     * @param time
     */
    void expire(String key, long time);

    /**
     * 设置
     *
     * @param key
     * @param value
     * @param expire
     */
    void set(String key, Object value, long expire);

    /**
     * 获取
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 删除
     *
     * @param key
     */
    void del(String... key);

    /**
     * 批量删除
     *
     * @param keys
     */
    void del(Collection<String> keys);

    /**
     * 查询前缀key
     *
     * @param preKey
     * @return
     */
    Set<String> scan(String preKey);

    /**
     * 查询前缀key的个数，可用来获取在线用户数
     *
     * @param preKey
     * @return
     */
    Long scanSize(String preKey);

    /**
     * 键值是否存在
     *
     * @param key
     * @return
     */
    Boolean hasKey(String key);

}
