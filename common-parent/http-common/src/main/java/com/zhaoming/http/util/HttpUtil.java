package com.zhaoming.http.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Map;

/**
 * http工具
 *
 * @author hhx
 */
@UtilityClass
public class HttpUtil {

    /**
     * 序列化参数
     * @param url
     * @param param
     * @return
     */
    public static String formatUrl(@NonNull String url, Map<String, Object> param){
        StringBuilder sb = new StringBuilder(url);
        sb.append('?');
        param.forEach((k, v) -> sb.append(k).append('=').append(v).append('&'));
        return sb.toString();
    }
}
