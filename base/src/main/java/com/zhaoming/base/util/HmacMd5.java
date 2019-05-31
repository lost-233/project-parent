package com.zhaoming.base.util;


import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Administrator
 * @date 2017/11/6
 */

public class HmacMd5 {
    public static final String CHARSET="UTF-8";
    public static final String ALGORITHM="HmacMD5";
    public static String encode(String key, String value){

        try {
            SecretKey sk = new SecretKeySpec(key.getBytes(CHARSET),ALGORITHM);
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(sk);
            byte[] result = mac.doFinal(value.getBytes(CHARSET));
            String rs=HexUtils.toHex(result).toLowerCase();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}