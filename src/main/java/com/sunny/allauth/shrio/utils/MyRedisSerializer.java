package com.sunny.allauth.shrio.utils;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.*;

/**
 * MyRedisSerializer
 *
 * @author lijunsong
 * @date 2019/8/22 9:01
 * @since 1.0
 */
@Slf4j
public class MyRedisSerializer implements RedisSerializer {
    @Override
    public byte[] serialize(Object o) throws SerializationException {
        byte[] result = null;

        if (o == null) {
            return new byte[0];
        }
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream)) {
            if (!(o instanceof Serializable)) {
                throw new IllegalArgumentException(MyRedisSerializer.class.getSimpleName()
                        + "require a Serializable object,but received is " + o.getClass().getName());
            }
            objectOutputStream.writeObject(o);
            objectOutputStream.flush();
            result = byteStream.toByteArray();
        } catch (Exception e) {
            log.error("Failed to serialize", e);
        }

        return result;
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        Object result = null;
        if (ArrayUtil.isEmpty(bytes)) {
            return null;
        }
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        try (ObjectInputStream inputStream = new ObjectInputStream(byteStream)) {
            result = inputStream.readObject();
        } catch (Exception e) {
            log.error("Failed to deserialize", e);
        }

        return result;
    }
}
