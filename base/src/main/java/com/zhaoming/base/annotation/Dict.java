package com.zhaoming.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标识业务返回值
 * @author hhx
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {
    /**
     * 示例值
     * @return
     */
    String value() default "";
    /**
     * 描述
     * @return
     */
    String desc() default "";
}
