package com.zhaoming.redis.singleton;

import com.zhaoming.redis.CacheInfo;
import com.zhaoming.redis.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 一个命名空间下只有一个Key
 * @author hhx
 */
@Slf4j
public class SingletonRepositoryImpl<T extends Serializable> extends Singleton {

    private final RedisTemplate<String,Object> redisTemplate;
    private final ValueOperations<String,Object> operations;

    public SingletonRepositoryImpl(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.operations = redisTemplate.opsForValue();
    }

    public T get() {
        return (T) operations.get(getKey());
    }

    public boolean put(T value) {
        CacheInfo cacheInfo = getCacheInfo();
        try {
            operations.set(cacheInfo.key,value);
            if(cacheInfo.timeout > 0){
                redisTemplate.expire(cacheInfo.key,cacheInfo.timeout,cacheInfo.timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("put singleton propKey error : " + cacheInfo.key);
        }
        return false;
    }

    public boolean put(T value, long seconds) {
        CacheInfo cacheInfo = getCacheInfo();
        try {
            operations.set(cacheInfo.key, value, seconds, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean exist() {
        return redisTemplate.hasKey(getKey());
    }

    public void remove() {
        redisTemplate.delete(getKey());
    }
}
