package com.zhaoming.redis;

import java.util.concurrent.TimeUnit;

/**
 * @author hhx
 */
public class CacheInfo {

    public String key;
    public long timeout;
    public TimeUnit timeUnit;

    /**
     * 获取包含Cache注解的类的Key
     * @param clazz
     * @return
     */
    public static String getKey(Class clazz){
        return getKey(clazz,null);
    }

    public static String getKey(Class clazz , String id){
        if(clazz.isAnnotationPresent(Cache.class)){
            Cache cache = (Cache) clazz.getAnnotation(Cache.class);
            return id == null?cache.value():cache.value() + id;
        }else{
            throw new RuntimeException(clazz.getName() + " 缺少注解 @Cache!");
        }
    }

    public static CacheInfo getCacheInfo(Class clazz){
        return getCacheInfo(clazz,null);
    }

    public static CacheInfo getCacheInfo(Class clazz , String id){
        if(clazz.isAnnotationPresent(Cache.class)){
            CacheInfo cacheInfo = new CacheInfo();
            Cache cache = (Cache) clazz.getAnnotation(Cache.class);
            cacheInfo.key = id == null?cache.value():cache.value() + id;
            cacheInfo.timeout = cache.timeout();
            cacheInfo.timeUnit = cache.timeUnit();
            return cacheInfo;
        }else{
            throw new RuntimeException(clazz.getName() + " 缺少注解 @Cache!");
        }
    }
}
