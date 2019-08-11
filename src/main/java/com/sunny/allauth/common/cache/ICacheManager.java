package com.sunny.allauth.common.cache;

import java.util.Set;

/**
 * ICacheManager
 *
 * @Description
 * @Author JasonLi
 * @Date 2019-08-11 20:54
 */
public interface ICacheManager {
    byte[] get(byte[] key);

    byte[] set(byte[] key, byte[] value, int expire);

    void del(byte[] key);

    Long dbSize(byte[] pattern);

    Set<byte[]> keys(byte[] pattern);

    void lPush(byte[] serialize, byte[] serialize1);
}
