package com.zhaoming.http.adapter;


import com.zhaoming.http.impl.RestTemplateHttpServiceImpl;

import java.util.Map;

/**
 * 接口适配器, 默认以RestTemplate实现
 * 防止httpService接口新增方法时,其他项目中自定义的httpService实现报错
 * @author hhx
 */
public class HttpServiceAdapter extends RestTemplateHttpServiceImpl {

    /**
     * @param url
     * @param param
     * @param resultType
     * @return
     */
    @Override
    public <T> T doGet(String url, Map<String, Object> param, Class<T> resultType) throws Exception{
        return super.doGet(url, param, resultType);
    }

    /**
     * @param url
     * @param param
     * @param resultType
     * @return
     */
    @Override
    public <T> T doPost(String url, Map<String, Object> param, Class<T> resultType) throws Exception{
        return super.doPost(url, param, resultType);
    }
}
