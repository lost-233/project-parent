package com.zhaoming.bean.platfrom.result;

import com.zhaoming.bean.platfrom.constant.ResultCode;
import com.zhaoming.bean.platfrom.constant.ResultMsg;
import org.apache.commons.lang3.StringUtils;


/**
 * 通用返回值枚举
 * @author huhuixin
 * @create 2017/11/23 下午5:34
 */

public enum CommonResult {

    SUCCESS(ResultCode.SUCCESS, ResultMsg.SUCCESS),
    SERVER_ERROR(ResultCode.SERVER_ERROR, ResultMsg.SERVER_ERROR),
    INVALID_SIGN(ResultCode.INVALID_SIGN, "签名无效"),
    INVALID_TIMESTAMP(ResultCode.INVALID_TIMESTAMP, "时间戳无效"),
    INVALID_METHOD(ResultCode.INVALID_METHOD, "无效的方法"),
    INVALID_PARAMTER(ResultCode.INVALID_PARAMTER, "请求参数不合法"),

    ;

    public final String result;
    public final String message;

    CommonResult(String result, String message) {
        this.result = result;
        this.message = message;
    }


    public static CommonResult get(String result) {
        for(CommonResult cr : values()){
            if(StringUtils.equals(result, cr.result)){
                return cr;
            }
        }
        return null;
    }
}
