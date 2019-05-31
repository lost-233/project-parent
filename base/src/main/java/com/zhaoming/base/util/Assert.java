package com.zhaoming.base.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 断言
 *
 * @author hhx
 */
public class Assert {

    /**
     * 表达式为真
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 函数运算结果为真
     * @param expression
     * @param message
     */
    public static void isTrue(Supplier<Boolean> expression, String message) {
        if (!expression.get()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 函数返回的对象不为空
     * @param t
     * @param message
     */
    public static <T> T notNull(T t, String message) {
        if (t == null) {
            throw new IllegalArgumentException(message);
        }
        return t;
    }

    /**
     * 数组不含空元素
     * @param array
     * @param message
     */
    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new IllegalArgumentException(message);
                }
            }
        }
    }

    /**
     * 字符串不为空
     * @param string
     * @param message
     */
    public static void notBlank(String string, String message) {
        if (StringUtils.isBlank(string)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 集合不含空元素
     * @param collection
     * @param message
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * List不为空
     * @param list
     * @param message
     */
    public static <T> List<T> listNotEmpty(List<T> list, String message) {
        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException(message);
        }
        return list;
    }

    /**
     * Set不为空
     * @param set
     * @param message
     */
    public static <T> Set<T> setNotEmpty(Set<T> set, String message) {
        if (CollectionUtils.isEmpty(set)) {
            throw new IllegalArgumentException(message);
        }
        return set;
    }

    /**
     * 数字是正数
     * @param number
     * @param message
     */
    public static void isPositive(Number number, String message) {
        if (number.intValue() <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 验证给定的流是否包含满足给定条件的值
     * 可用于判断数据有效性, 如校验参数必须是枚举中的某个值
     * @param tStream 流
     * @param predicate 匹配条件
     * @param message 找不到是抛出的异常信息
     * @param <T> 元素类型
     * @return 返回找到的元素
     */
    public static <T> T isEffective(Stream<T> tStream, Predicate<T> predicate, String message){
        return tStream.filter(predicate).findFirst().orElseThrow(() -> new IllegalArgumentException(message));
    }
}
