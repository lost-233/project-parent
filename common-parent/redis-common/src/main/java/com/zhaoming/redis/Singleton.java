package com.zhaoming.redis;

/**
 * @author hhx
 */
public class Singleton<T> {
    protected String getKey(){
        return CacheInfo.getKey(getClass());
    }
    protected CacheInfo getCacheInfo(){
        return CacheInfo.getCacheInfo(getClass());
    }
}
