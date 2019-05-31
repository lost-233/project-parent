package com.zhaoming.base.util;

import lombok.experimental.UtilityClass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码加密工具
 * 对用户输入的密码进行加密
 * @author hhx
 */
@UtilityClass
public class PasswordUtil {

    /**
     * 根据用户输入的密码获取加密后的密文
     * @param password
     * @return
     */
    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
            byte[] results = md.digest(password.getBytes());
            //将得到的字节数组变成字符串返回
            String resultString = byteArrayToHexString(results);
            return resultString.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("加密失败!" + e.getMessage());
        }
    }

    /**
     * 转换字节数组为十六进制字符串
     * @param   b  字节数组
     * @return    十六进制字符串
     */
    private static String byteArrayToHexString(byte[] b){
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /** 将一个字节转化成十六进制形式的字符串  */
    private static String byteToHexString(byte b){
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    /** 十六进制下数字到字符的映射数组 */
    private final static String[] HEX_DIGITS = {"0", "1", "2", "3", "4",
            "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
}
