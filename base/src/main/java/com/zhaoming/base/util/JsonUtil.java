package com.zhaoming.base.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhaoming.base.annotation.Dict;
import com.zhaoming.base.bean.Page;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author hhx
 */
@Slf4j
@UtilityClass
@SuppressWarnings("all")
public class JsonUtil {
    /**
     * 递归属性标识只生成一次
     */
    private static boolean flag=false;

    private final static ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 获取一个自定义class的json格式
     * @param clazz
     * @return
     */
    public static String class2Json(Class clazz) {
        try {
            flag=false;
            Object obj = getInstance(clazz);
            // 利用注解填充示例值
            setValue(obj);
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(clazz.getName() + ":" + e.getMessage());
        }
        return "{}";
    }

    /**
     * 转换成json
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转换成对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T json2object(String json, Class<T> clazz) {
        try {
            if(StringUtils.isNoneEmpty(json)){
                return MAPPER.readValue(json,clazz);
            }
        } catch (IOException e) {
            //log.error("json 字符串 '{}' 反序列化为 对象 {} 失败 , message : {} .", json, clazz.getName(), e.getMessage());
        }
        return null;
    }

    /**
     * 将对象转为 字符串(基本类型) 或者 json(可序列化类型) 格式
     * @param arg
     * @return
     */
    public static String formatArg(Object arg){
        try {
            if(ObjectUtil.isBaseType(arg.getClass())){
                return arg.toString();
            }
            return JsonUtil.toJson(arg);
        } catch (Exception e) {
            return arg.toString();
        }
    }

    /**
     * json转换成数组
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> json2list(String json, Class<T> clazz) {
        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        try {
            return MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            //log.error("json 字符串 '{}' 反序列化为 List<T> {} 失败 , message : {} .", json, clazz.getName(), e.getMessage());
        }
        return null;
    }


    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 利用注解填充示例值
     * @param field
     * @return
     */
    public static void setValue(Object obj) {
        try {
            Class clazz = obj.getClass();
            Method[] methods = clazz.getMethods();
            for (Method method : methods){
                if(method.getName().startsWith("set")){
                    Field field = ObjectUtil.getFieldByClass(clazz, ObjectUtil.getFieldNameByMethodName(method.getName()));
                    Type type = field.getType();
                    Class filedClazz = (Class) type;
                    if(ObjectUtil.isBaseType(filedClazz)){
                        Dict dict = field.getAnnotation(Dict.class);
                        if(StringUtils.isNotBlank(dict.value())){
                            try {
                                method.invoke(obj, convertValue(dict.value(), filedClazz));
                            } catch (Exception e) {
                                log.warn("class `{}` field `{}` set value `{}` error. cause: {}",
                                        clazz.getName(), field.getName(), dict.value(), e.getMessage());
                            }
                        }
                    }else{
                        // 返回的不是基础类型, 构建get方法
                        Method getMethod = clazz.getMethod("get" + method.getName().substring(3));
                        if(getMethod != null){
                            Object fieldValue = getMethod.invoke(obj);
                            Class returnClazz = getMethod.getReturnType();
                            if (Collection.class.isAssignableFrom(returnClazz)){
                                ((Collection) fieldValue).forEach(JsonUtil::setValue);
                            } else if (Map.class.isAssignableFrom(returnClazz)){
                                ((Map) fieldValue).forEach((k, v) -> setValue(v));
                            } else if (returnClazz.equals(Page.class)){
                                setValue(fieldValue);
                            } else {
                                setValue(fieldValue);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("msg : {}", e.getMessage());
        }
    }

    /**
     * 根据class 获取实例
     * @param clazz
     * @return
     * @throws Exception
     */
    public static Object getInstance(Class clazz) {
        try {
            if(ObjectUtil.isBaseType(clazz)){
                return getValueByType(clazz);
            }
            Object obj = clazz.newInstance();
            Method[] methods = clazz.getMethods();
            for (Method method : methods){
                if(method.getName().startsWith("set")){
                    Field field = ObjectUtil.getFieldByClass(clazz, ObjectUtil.getFieldNameByMethodName(method.getName()));
                    if(field == null){
                        method.invoke(obj,null);
                    }else{
                        Type genericType = field.getGenericType();
                        Class paramClazz = method.getParameterTypes()[0];
                        // 是否带泛型
                        boolean hasGeneric = genericType != null && genericType instanceof ParameterizedType;
                        if(ObjectUtil.isBaseType(paramClazz)){
                            // 基础数据类型
                            method.invoke(obj,getValueByType(paramClazz));
                        } else if(paramClazz.equals(List.class) || paramClazz.equals(Collection.class)){
                            // 列表
                            if(hasGeneric) {
                                List<Object> list = new ArrayList<>();
                                Class<?> genericClazz = ObjectUtil.getFirstGenericClazz(genericType);
                                // 避免循环嵌套
                                if (!clazz.equals(genericClazz)) {
                                    list.add(getInstance(genericClazz));
                                    method.invoke(obj, list);
                                }else if(!flag){
                                    flag=true;
                                    list.add(getInstance(genericClazz));
                                    method.invoke(obj, list);
                                }
                            }else {
                                method.invoke(obj, ListUtils.EMPTY_LIST);
                            }
                        } else if(paramClazz.equals(Set.class)){
                            // 集合
                            if(hasGeneric) {
                                Set<Object> set = new HashSet<>();
                                Class<?> genericClazz = ObjectUtil.getFirstGenericClazz(genericType);
                                if (!clazz.equals(genericClazz)) {
                                    set.add(getInstance(genericClazz));
                                    method.invoke(obj, set);
                                }else if(!flag){
                                    flag=true;
                                    set.add(getInstance(genericClazz));
                                    method.invoke(obj, set);
                                }
                            }else {
                                method.invoke(obj, SetUtils.EMPTY_SET);
                            }
                        } else if(paramClazz.equals(Map.class)){
                            // 映射
                            if(hasGeneric) {
                                Type[] arguments = ((ParameterizedType) genericType).getActualTypeArguments();
                                Map map = new HashMap(1);
                                map.put(getInstance((Class<?>) arguments[0]),getInstance((Class<?>) arguments[1]));
                                method.invoke(obj,map);
                            }else {
                                method.invoke(obj, MapUtils.EMPTY_MAP);
                            }
                        } else if (paramClazz.equals(Page.class)){
                            // 分页
                            if (hasGeneric) {
                                Page page = new Page();
                                Class<?> genericClazz = ObjectUtil.getFirstGenericClazz(genericType);
                                page.setList(Arrays.asList(getInstance(genericClazz)));
                                method.invoke(obj, page);
                            } else {
                                method.invoke(obj, Page.EMPTY_PAGE);
                            }
                        } else if(paramClazz.equals(Date.class)){
                            // 日期
                            method.invoke(obj, new Date());
                        } else {
                            // 普通对象
                            method.invoke(obj,getInstance(paramClazz));
                        }
                    }
                }
            }
            return obj;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }


    /**
     * 根据class类型获取默认值
     * @param clazz
     * @return
     * @throws Exception
     */
    private static Object getValueByType(Class<?> clazz) throws Exception {
        if(clazz == String.class){
            return "String";
        }else if(clazz == Integer.class || clazz == int.class){
            return 1;
        }else if(clazz == Boolean.class || clazz == boolean.class){
            return true;
        }else if(clazz == Long.class || clazz == long.class){
            return 10000000000L;
        }else if(clazz == Float.class || clazz == float.class){
            return 1.0F;
        }else if(clazz == Double.class || clazz == double.class){
            return 1.00D;
        }else if(clazz == Character.class || clazz == char.class){
            return 'c';
        }else if(clazz == BigDecimal.class){
            return 1;
        }
        return null;
    }


    /**
     * 根据
     * @param fieldValue
     * @param fieldClazz
     * @return
     */
    private static Object convertValue(String fieldValue, Class fieldClazz){
        try {
            if(fieldClazz == String.class){
                return fieldValue;
            }else if(fieldClazz == Integer.class || fieldClazz == int.class){
                return Integer.valueOf(fieldValue);
            }else if(fieldClazz == Boolean.class || fieldClazz == boolean.class){
                return Boolean.parseBoolean(fieldValue);
            }else if(fieldClazz == Long.class || fieldClazz == long.class){
                return Long.valueOf(fieldValue);
            }else if(fieldClazz == Float.class || fieldClazz == float.class){
                return Float.valueOf(fieldValue);
            }else if(fieldClazz == Double.class || fieldClazz == double.class){
                return Double.valueOf(fieldValue);
            }else if(fieldClazz == Character.class || fieldClazz == char.class){
                return fieldValue.charAt(0);
            }else if(fieldClazz == BigDecimal.class){
                return new BigDecimal(fieldValue);
            }
        } catch (Exception e) {
            log.warn("format value {} to class {} error. message : {}",
                    fieldValue, fieldClazz.getName(), e.getMessage());
        }
        return null;
    }

}
