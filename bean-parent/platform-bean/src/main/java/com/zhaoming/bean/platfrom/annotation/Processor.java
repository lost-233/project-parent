package com.zhaoming.bean.platfrom.annotation;


import com.zhaoming.bean.platfrom.ServiceResponse;
import com.zhaoming.bean.platfrom.enums.Versions;
import com.zhaoming.bean.platfrom.enums.Service;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识业务处理器
 *
 * @author hhx
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Processor {
    /**
     * 接口描述(用途)
     *
     * @return string
     */
    String desc();

    /**
     * 接口分类的枚举
     *
     * @return Service
     */
    Service service();

    /**
     * 方法值(接口标识)
     *
     * @return String
     */
    String method();

    /**
     * 接口版本号
     * @return Level
     */
    Versions level() default Versions.V1;
    /**
     * 是否弃用
     *
     * @return boolean
     */
    boolean deprecated() default false;

    /**
     * 返回值实体类
     *
     * @return Class
     */
    Class<? extends ServiceResponse> response() default ServiceResponse.class;
}
