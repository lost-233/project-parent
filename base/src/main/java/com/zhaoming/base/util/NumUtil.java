package com.zhaoming.base.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 数字和字符串操作工具
 *
 * @author zm
 */
@UtilityClass
public class NumUtil {
    /**
     * 生成随机的字符串
     *
     * @param length 长度
     * @return 结果
     */
    public static String getRandomStr(int length) {
        StringBuilder key = new StringBuilder();
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        while (key.length() < length) {
            char charAt = chars.charAt(RandomUtils.nextInt(0, chars.length()));
            key.append(charAt);
        }
        return key.toString();
    }

    /**
     * 根据长度生成随机数字字符串(不足前面补0)
     * @param length 位数
     * @return 结果
     */
    public static String getRandomNum(int length) {
        Integer max = Integer.valueOf(StringUtils.leftPad("", length, "9"));
        //如果不足三位前面补0
        return StringUtils.leftPad(String.valueOf(RandomUtils.nextInt(0, max)), length, "0");
    }

}
