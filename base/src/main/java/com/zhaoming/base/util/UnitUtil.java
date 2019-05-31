package com.zhaoming.base.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

/**
 * 计量单位转换工具
 * @author hhx
 */
@UtilityClass
public class UnitUtil {

    /**
     * 分转元字符串
     * @param d
     * @return
     */
    public static String fen2yuan2string(Long d){
        return new BigDecimal(d).movePointLeft(2).toString();
    }

    /**
     * 分转元
     * @param d
     * @return double
     */
    public static double fen2yuan(Long d){
        return new BigDecimal(d).movePointLeft(2).doubleValue();
    }

    /**
     * 分转元 返回 BigDecimal
     * @param d
     * @return BigDecimal
     */
    public static BigDecimal fen2yuan2BigDecimal(Long d){
        return new BigDecimal(d).movePointLeft(2);
    }

    /**
     * 元转分
     * @param d
     * @return
     */
    public static Long yuan2fen(BigDecimal d){
        return d.movePointRight(2).longValue();
    }
}
