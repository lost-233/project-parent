package com.zhaoming.base.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * @author hhx
 */
@Slf4j
@UtilityClass
public class ObjectUtil {

    /**
     * 获取实例中指定成员变量的值
     * @param field
     * @param o
     * @return
     */
    public static Object getFieldValueByName(Field field, Object o) {
        try {
            field.setAccessible(true);
            return field.get(o);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据函数名称获取成员变量名称
     * @param methodName
     * @return
     */
    public static String getFieldNameByMethodName(String methodName){
        return methodName.substring(3,4).toLowerCase() + methodName.substring(4);
    }

    /**
     * 获取泛型class
     */
    public static Class<?> getFirstGenericClazz(Type genericType){
        ParameterizedType pt = (ParameterizedType) genericType;
        return (Class<?>)pt.getActualTypeArguments()[0];
    }

    /**
     * 获取泛型class
     */
    public static Type[] getGenericClazz(Type genericType){
        ParameterizedType pt = (ParameterizedType) genericType;
        return pt.getActualTypeArguments();
    }

    /**
     * 判断是否为基本数据类型
     * @param clazz
     * @return
     * @throws Exception
     */
    public static boolean isBaseType(Class<?> clazz) throws Exception {
        if (clazz == String.class
                || clazz == Integer.class || clazz == int.class
                || clazz == Boolean.class || clazz == boolean.class
                || clazz == Long.class || clazz == long.class
                || clazz == Float.class || clazz == float.class
                || clazz == Double.class || clazz == double.class
                || clazz == Character.class || clazz == char.class
                || clazz == BigDecimal.class){
            return true;
        }
        return false;
    }

    /**
     * 通过名称和Class查找Filed属性
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getFieldByClass(Class clazz,String fieldName){
        Field field = null;
        if(!clazz.equals(Object.class)){
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                log.warn(String.format("filed %s is not in class %s",fieldName,clazz.getName()));
            }
            if(field == null){
                field = getFieldByClass(clazz.getSuperclass(),fieldName);
            }
        }
        return field;
    }


    /**
     * org 的属性赋值给 dest
     * @param dest
     * @param orig
     * @return
     */
    public static void copyProperties(final Object dest, final Object orig) {
        try {
            PropertyUtils.copyProperties(dest, orig);
        } catch (Exception e) {
            throw new RuntimeException("属性复制异常!");
        }
    }
}
