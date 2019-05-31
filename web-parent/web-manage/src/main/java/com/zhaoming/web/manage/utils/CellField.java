package com.zhaoming.web.manage.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义注解
 * @author：   Rodge
 * @time：     2017年12月26日 下午8:32:10
 * @version：  V1.0.0
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CellField {
 
	/**
     * 文件列名
     */
	String name() default "";
    
}