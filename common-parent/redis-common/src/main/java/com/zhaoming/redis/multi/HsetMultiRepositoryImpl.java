package com.zhaoming.redis.multi;

import com.zhaoming.redis.Multi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Map;

/**
 * Hash Set 方式缓存数据, 很多个键值 ,每个键值存储很多个Key
 * 如 一个用户缓存了很多地址信息 则 主key 为openId  子key 为地址ID
 * @author huhuixin
 * @param <T>
 */
@Slf4j
public class HsetMultiRepositoryImpl<T extends Serializable> extends Multi {

    private final RedisTemplate<String,Object> redisTemplate;
    private final HashOperations<String, String, T> opsForHash;

    @Autowired
    public HsetMultiRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.opsForHash = redisTemplate.opsForHash();
    }

    public boolean exist(String key) {
        return redisTemplate.hasKey(getKey(key));
    }

    public Map<String, T> getAll(String key) {
        return opsForHash.entries(getKey(key));
    }

    public void putAll(String key, Map<String, T> entries) {
        opsForHash.putAll(getKey(key),entries);
    }

    public boolean hasKey(String key, String childKey) {
        return opsForHash.hasKey(getKey(key),childKey);
    }

    public T get(String key, String childKey) {
        return opsForHash.get(getKey(key),childKey);
    }

    public void put(String key, String childKey, T value) {
        opsForHash.put(getKey(key),childKey,value);
    }

    public void clear(String key) {
        redisTemplate.delete(getKey(key));
    }

    public Long remove(String key, String... childKeys) {
        return opsForHash.delete(getKey(key),childKeys);
    }
}
