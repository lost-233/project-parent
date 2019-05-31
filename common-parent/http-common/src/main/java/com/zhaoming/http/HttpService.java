package com.zhaoming.http;

import java.util.Map;

/**
 * http
 *
 * @author hhx
 */
public interface HttpService {

    /**
     * get
     * @param url
     * @param param
     * @param resultType
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T doGet(String url, Map<String, Object> param, Class<T> resultType) throws Exception;

    /**
     * post
     * @param url
     * @param param
     * @param resultType
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T doPost(String url, Map<String, Object> param, Class<T> resultType) throws Exception;
}
