package com.zhaoming.redis.singleton;

import com.zhaoming.redis.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Map;

/**
 * Hash Set 方式缓存数据, 一个键值存储了很多个Key
 * 如 缓存库存信息  主key 只有一个  子key 为日期+餐点+食品ID
 * @author huhuixin
 * @param <T>
 */
@Slf4j
public class HsetSingletonRepositoryImpl<T extends Serializable> extends Singleton {

    protected final RedisTemplate<String,Object> redisTemplate;
    protected final HashOperations<String, String, T> opsForHash;

    @Autowired
    public HsetSingletonRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.opsForHash = redisTemplate.opsForHash();
    }

    public boolean exist() {
        return redisTemplate.hasKey(getKey());
    }

    public Map<String, T> getAll() {
        return opsForHash.entries(getKey());
    }

    public void putAll(Map<String, T> entries) {
        opsForHash.putAll(getKey(),entries);
    }

    public boolean hasKey(String key) {
        return opsForHash.hasKey(getKey(),key);
    }

    public T get(String key) {
        return opsForHash.get(getKey(),key);
    }

    public void put(String key, T value) {
        opsForHash.put(getKey(),key,value);
    }

    public void clear() {
        redisTemplate.delete(getKey());
    }

    public Long remove(String... keys) {
        return opsForHash.delete(getKey(),keys);
    }
}
