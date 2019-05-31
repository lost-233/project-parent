package com.zhaoming.redis.multi;

import com.zhaoming.redis.Multi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.List;

/**
 * 一个命名空间下有多个key值
 * @author hhx
 */
@Slf4j
public class ListMultiRepositoryImpl<T extends Serializable> extends Multi {

    private final RedisTemplate<String,Object> redisTemplate;
    private final ListOperations<String,Object> operations;

    public ListMultiRepositoryImpl(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.operations = redisTemplate.opsForList();
    }

    public List<T> getAll(String key){
        return (List) operations.range(getKey(key),0,-1);
    }

    public void leftPushAll(String key, List<T> values) {
        String key_ = getKey(key);
        for (T value : values) {
            operations.leftPush(key_, value);
        }
    }

    public void rightPushAll(String key, List<T> values) {
        String key_ = getKey(key);
        for (T value : values) {
            operations.rightPush(key_, value);
        }
    }

    public void leftPush(String key,T value) {
        operations.leftPush(getKey(key),value);
    }

    public Long size(String key){
        return operations.size(getKey(key));
    }

    public boolean exist(String key) {
        return size(key) > 0;
    }

    public void remove(String key) {
        redisTemplate.delete(getKey(key));
    }

}
