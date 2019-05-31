package com.zhaoming.base.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huhuixin
 */
@UtilityClass
public class RegexUtils {

    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     * @param soap
     * @param rgex
     * @return
     */
    public static String getSubUtilSimple(String soap,String rgex){
        Pattern pattern = Pattern.compile(rgex);
        Matcher m = pattern.matcher(soap);
        while(m.find()){
            return m.group(1);
        }
        return "";
    }

    /**
     * 手机号匹配
     */
    public static final Pattern PATTERN_PHONE = Pattern.compile("^1[3-9]\\d{9}$");
    /**
     * 匹配全数字
     */
    public static final Pattern IS_NUMBER = Pattern.compile("\\d+");
    /**
     * 匹配价格 从不以0开头的整数或以0开头的整数加两位以内小数的位置开始 , 匹配最大8位整数或8位整数加两位以内小数
     */
    public static final Pattern IS_PRICE = Pattern.compile("(?!^0*(\\.0{1,2})?$)^\\d{1,8}(\\.\\d{1,2})?$");
    /**
     * 匹配gps 格式 29.111111111,139.5555555555
     */
    public static final Pattern IS_GPS = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?+(\\,){1}+(\\-|\\+)?\\d+(\\.\\d+)?$");

    /**
     * 校验手机号
     * @param str
     * @return
     */
    public static boolean isPhone(String str){
        if(str == null){
            return false;
        }
        Matcher matcher = PATTERN_PHONE.matcher(str);
        return matcher.matches();
    }
}
