package com.zhaoming.http.impl;

import com.zhaoming.base.util.JsonUtil;
import com.zhaoming.http.HttpService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * http
 *
 * @author hhx
 */
public class RestTemplateHttpServiceImpl implements HttpService {

    private RestTemplate restTemplate;

    public RestTemplateHttpServiceImpl() {
        restTemplate = new RestTemplate();
    }

    @Override
    public <T> T doGet(String url, Map<String, Object> param, Class<T> resultType) throws Exception {
        String json = restTemplate.getForObject(url, String.class, param);
        return JsonUtil.json2object(json, resultType);
    }

    @Override
    public <T> T doPost(String url, Map<String, Object> param, Class<T> resultType) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        param.forEach((k, v) -> map.put(k, Collections.singletonList(v.toString())));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String json = restTemplate.postForObject(url, request, String.class);
        return JsonUtil.json2object(json, resultType);
    }
}
