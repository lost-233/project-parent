package com.zhaoming.redis.multi;

import com.zhaoming.redis.CacheInfo;
import com.zhaoming.redis.Multi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 一个命名空间下有多个key值
 * @author hhx
 */
@Slf4j
public class MultiRepositoryImpl<T extends Serializable> extends Multi {

    private final RedisTemplate<String,Object> redisTemplate;
    private final ValueOperations<String,Object> operations;

    public MultiRepositoryImpl(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.operations = redisTemplate.opsForValue();
    }

    public T get(String key) {
        return (T) operations.get(getKey(key));
    }

    public boolean put(T value,String key) {
        CacheInfo cacheInfo = getCacheInfo();
        try {
            operations.set(cacheInfo.key + key, value);
            if(cacheInfo.timeout > 0){
                redisTemplate.expire(cacheInfo.key  + key,cacheInfo.timeout,cacheInfo.timeUnit);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("put multi propKey error : " + key);
        }
        return false;
    }

    public boolean put(T value, String key, long seconds) {
        CacheInfo cacheInfo = getCacheInfo();
        try {
            operations.set(cacheInfo.key + key, value, seconds, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean exist(String key) {
        return redisTemplate.hasKey(getKey(key));
    }

    public void remove(String key) {
        redisTemplate.delete(getKey(key));
    }

    public void expire(String key) {
        CacheInfo cacheInfo = getCacheInfo();
        redisTemplate.expire(cacheInfo.key + key,cacheInfo.timeout,cacheInfo.timeUnit);
    }
}
