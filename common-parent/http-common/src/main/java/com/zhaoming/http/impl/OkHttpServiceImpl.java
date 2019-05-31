package com.zhaoming.http.impl;

import com.zhaoming.base.util.JsonUtil;
import com.zhaoming.http.HttpService;
import com.zhaoming.http.util.HttpUtil;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;

/**
 * http
 *
 * @author hhx
 */
public class OkHttpServiceImpl implements HttpService {

    private OkHttpClient client;

    public OkHttpServiceImpl() {
        this.client = new OkHttpClient();
    }

    @Override
    public <T> T doGet(String url, Map<String, Object> param, Class<T> resultType) throws Exception {
        Request request = new Request.Builder()
                .url(HttpUtil.formatUrl(url, param))
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        Response response = client.newCall(request).execute();
        return JsonUtil.json2object(response.body().string(), resultType);
    }

    @Override
    public <T> T doPost(String url, Map<String, Object> param, Class<T> resultType) throws Exception {
        FormBody.Builder builder = new FormBody.Builder();
        param.forEach((k, v) -> builder.add(k, v.toString()));
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .addHeader("cache-control", "no-cache")
                .build();
        Response response = client.newCall(request).execute();
        return JsonUtil.json2object(response.body().string(), resultType);
    }
}
