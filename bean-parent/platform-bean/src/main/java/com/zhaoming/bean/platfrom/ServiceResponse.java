package com.zhaoming.bean.platfrom;

import com.zhaoming.bean.platfrom.constant.ResultCode;
import com.zhaoming.bean.platfrom.constant.ResultMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口响应结果
 *
 * @author huhuixin
 * @create 2017/11/23 下午5:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {

    public static final ServiceResponse SERVER_ERROR = new ServiceResponse(ResultCode.SERVER_ERROR, ResultMsg.SERVER_ERROR);

    protected String result;
    protected String message;

    public static ServiceResponse paramNotNull(String param){
        return new ServiceResponse(ResultCode.INVALID_PARAMTER,String.format("缺少参数 : '%s'!", param));
    }

    public static ServiceResponse paramValueNotNull(String param){
        return new ServiceResponse(ResultCode.INVALID_PARAMTER,String.format("参数 : '%s' 值为空!", param));
    }

    public static ServiceResponse paramCheckFail(String param) {
        return new ServiceResponse(ResultCode.INVALID_PARAMTER,String.format("参数 : '%s' 格式错误!", param));
    }
}
