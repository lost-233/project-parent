package com.zhaoming.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用状态
 *
 * @author hhx
 */
@Getter
@AllArgsConstructor
public enum CommonStatus {
    /**
     * 表示否定
     */
    NO(0),
    /**
     * 表示肯定
     */
    YES(1);
    private Integer status;
}
