package com.zhaoming.redis.singleton;

import com.zhaoming.redis.Singleton;
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
public class ListSingletonRepositoryImpl<T extends Serializable> extends Singleton {

    private final RedisTemplate<String,Object> redisTemplate;
    private final ListOperations<String,Object> operations;

    public ListSingletonRepositoryImpl(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.operations = redisTemplate.opsForList();
    }

    public List<T> getAll(){
        return (List) operations.range(getKey(),0,-1);
    }

    public void leftPush(T value) {
        operations.leftPush(getKey(),value);
    }

    public void rightPush(T value) {
        operations.rightPush(getKey(),value);
    }

    public T rightPop(){
        return (T) operations.rightPop(getKey());
    }

    public Long size(){
        return operations.size(getKey());
    }

    public boolean exist() {
        return size() > 0;
    }

    public void remove() {
        redisTemplate.delete(getKey());
    }
}
