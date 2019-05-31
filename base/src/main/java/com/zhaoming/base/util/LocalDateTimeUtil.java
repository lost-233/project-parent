package com.zhaoming.base.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * localDateTime 工具类
 *
 * @author hhx
 */
public class LocalDateTimeUtil {

    //获取当前时间的LocalDateTime对象
    //LocalDateTime.now();

    //根据年月日构建LocalDateTime
    //LocalDateTime.of();

    //比较日期先后
    //LocalDateTime.now().isBefore(),
    //LocalDateTime.now().isAfter(),

    /**
     * Date转换为LocalDateTime
     */
    public static LocalDateTime convertFromDate(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转换为Date
     */
    public static Date convertToDate(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 获取指定日期的毫秒
     */
    public static Long getMilli(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取指定日期的秒
     */
    public static Long getSeconds(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 获取指定时间的指定格式
     */
    public static String format(LocalDateTime time, String pattern) {
        if (time == null) {
            return null;
        }
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前时间的指定格式
     */
    public static String formatNow(String pattern) {
        return format(LocalDateTime.now(), pattern);
    }

    /**
     * 获取一天的开始时间，2017,7,22 00:00
     */
    public static LocalDateTime getDayStart(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return time.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    /**
     * 获取一天的结束时间，2017,7,22 23:59:59.999999999
     */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return time.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
    }
}
