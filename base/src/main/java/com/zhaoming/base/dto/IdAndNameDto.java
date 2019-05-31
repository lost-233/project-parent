package com.zhaoming.base.dto;

import com.zhaoming.base.annotation.Dict;
import lombok.Data;

/**
 * 获取的内容只包含id和name
 *
 * @author hhx
 */
@Data
public class IdAndNameDto {
    @Dict(desc = "Id", value = "3")
    private Integer id;
    @Dict(desc = "名称", value = "小明")
    private String name;
}
