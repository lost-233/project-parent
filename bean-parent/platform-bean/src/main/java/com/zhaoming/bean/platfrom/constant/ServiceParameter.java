package com.zhaoming.bean.platfrom.constant;


import com.zhaoming.base.util.JsonUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 业务请求参数
 * @author huhuixin on 2017/11/23.
 */
public interface ServiceParameter {

    /**
     * 参数名
     *
     * @return
     */
    String name();

    /**
     * 是否必须
     *
     * @return
     */
    boolean isRequired();

    /**
     * 默认值
     *
     * @return
     */
    String defaultValue();

    /**
     * 校验参数
     * @return
     */
    Predicate<String> check();

    Predicate DEFAULT_CHECK = t -> true;

    /**
     * 参数必须无默认值
     * @param name
     * @return
     */
    static ServiceParameter getServiceParameter(String name){
        return getServiceParameter(name, true, null, DEFAULT_CHECK);
    }

    /**
     * 参数非必须默认值为null
     * @param name
     * @return
     */
    static ServiceParameter getServiceParameterNotRequired(String name){
        return getServiceParameter(name, false, null, DEFAULT_CHECK);
    }

    /**
     * 共用参数构造
     * @param name
     * @param isRequired
     * @param defaultValue
     * @return
     */
    static ServiceParameter getServiceParameter(String name, boolean isRequired, String defaultValue){
        return getServiceParameter(name, isRequired, defaultValue, DEFAULT_CHECK);
    }

    static ServiceParameter getServiceParameter(String name, Predicate<String> check){
        return getServiceParameter(name, true, null, check);
    }

    static ServiceParameter getServiceParameter(String name, String defaultValue, Predicate<String> check){
        return getServiceParameter(name, true, defaultValue, check);
    }

    static ServiceParameter getServiceParameter(String name, boolean isRequired, String defaultValue, Predicate<String> check){
        return new ServiceParameter(){
            @Override
            public String name() {
                return name;
            }

            @Override
            public boolean isRequired() {
                return isRequired;
            }

            @Override
            public String defaultValue() {
                return defaultValue;
            }

            @Override
            public Predicate<String> check() {
                return check;
            }
        };
    }
    /**
     * 获取参数对应的String的值
     * @param parameters
     * @param serviceParameter
     * @return
     */
    static String getString(Map<String, String> parameters, ServiceParameter serviceParameter){
        return parameters.get(serviceParameter.name());
    }
    /**
     * 获取参数对应的Integer的值
     * @param parameters
     * @param serviceParameter
     * @return
     */
    static Integer getInteger(Map<String, String> parameters, ServiceParameter serviceParameter){
        return parameters.get(serviceParameter.name())==null?null:Integer.valueOf(parameters.get(serviceParameter.name()));
    }

    /**
     * 获取参数中的json字符串转换成的对象
     * @param parameters
     * @param serviceParameter
     * @param clazz
     * @return
     */
    static <T> T getObjectByJsonString(Map<String, String> parameters, ServiceParameter serviceParameter, Class<T> clazz) {
        return parameters.get(serviceParameter.name())==null?null:JsonUtil.json2object(parameters.get(serviceParameter.name()), clazz);
    }

    /**
     * 获取参数中的json字符串转换成的对象 返回List
     * @param parameters
     * @param serviceParameter
     * @param clazz
     * @return
     */
    static <T> List<T> getListByJsonString(Map<String, String> parameters, ServiceParameter serviceParameter, Class<T> clazz) {
        return parameters.get(serviceParameter.name())==null?null:JsonUtil.json2list(parameters.get(serviceParameter.name()), clazz);
    }
}
