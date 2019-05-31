package com.zhaoming.redis;

/**
 * @author hhx
 */
public class Multi {
    protected String getKey(String key){
        return CacheInfo.getKey(getClass())+key;
    }
    protected CacheInfo getCacheInfo(){
        return CacheInfo.getCacheInfo(getClass());
    }
}
