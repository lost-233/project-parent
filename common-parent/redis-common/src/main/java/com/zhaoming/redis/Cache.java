package com.zhaoming.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author hhx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface Cache {
    /**
     * 命名空间
     * @return
     */
    String value();
    /**
     * 时间长度 0 代表永久保存
     * @return
     */
    long timeout() default 0;
    /**
     * 时间单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
