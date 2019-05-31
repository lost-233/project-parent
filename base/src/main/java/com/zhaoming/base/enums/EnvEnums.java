package com.zhaoming.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hhx
 */
@Getter
@AllArgsConstructor
public enum EnvEnums {
    /**
     * 本地环境
     */
    DEV("dev"),
    /**
     * 测试环境
     */
    TEST("test"),
    /**
     * 线上环境
     */
    ONLINE("online"),
    ;
    private String env;
}
