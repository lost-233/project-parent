package com.zhaoming.bean.platfrom.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hhx
 */
@Getter
@AllArgsConstructor
public enum Service {
    /**
     * 服务名称列表
     */
    PLATFORM("test"),
    ;

    private String name;
}
