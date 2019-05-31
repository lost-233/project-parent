package com.zhaoming.base.service.param;

import lombok.Data;

/**
 * 访问服务层携带的用户通行证统一ID (车主端 2018-11-13)
 * @author hhx
 */
@Data
public class BasePassportSequence implements PassportSequenceInterface{
    private String passportSequence;
}
