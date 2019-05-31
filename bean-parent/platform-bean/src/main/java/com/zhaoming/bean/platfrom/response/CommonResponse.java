package com.zhaoming.bean.platfrom.response;

import com.zhaoming.bean.platfrom.ServiceResponse;
import com.zhaoming.bean.platfrom.result.CommonResult;
import lombok.NoArgsConstructor;

/**
 * 通用返回值
 *
 * @author huhuixin
 * @create 2017/11/23 下午5:33
 */
@NoArgsConstructor
public class CommonResponse extends ServiceResponse {

    public CommonResponse(CommonResult commonResult) {
        super(commonResult.result, commonResult.message);
    }


}
