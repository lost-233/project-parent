package com.zhaoming.base.bean;

import lombok.Data;

/**
 * 描述: 分页查询
 *
 * @author huhuixin
 * @create 2017/12/29 下午3:06
 */
@Data
public class PageCondition {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
