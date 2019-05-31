package com.zhaoming.redis.multi;

import com.zhaoming.redis.CacheInfo;
import com.zhaoming.redis.Multi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * 一个命名空间下有多个key值
 * @author hhx
 */
@Slf4j
public class StringMultiRepositoryImpl extends Multi {

    private final StringRedisTemplate redisTemplate;
    private final ValueOperations<String,String> operations;

    public StringMultiRepositoryImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.operations = redisTemplate.opsForValue();
    }

    public String get(String key) {
        return operations.get(getKey(key));
    }

    public boolean put(String value, String key) {
        CacheInfo cacheInfo = getCacheInfo();
        try {
            operations.set(cacheInfo.key + key, value);
            if(cacheInfo.timeout > 0){
                redisTemplate.expire(cacheInfo.key+ key,cacheInfo.timeout,cacheInfo.timeUnit);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("put multi json propKey error : " + key);
        }
        return false;
    }

    public boolean exist(String key) {
        return redisTemplate.hasKey(getKey(key));
    }

    public void remove(String key) {
        redisTemplate.delete(getKey(key));
    }
}
