package com.zhaoming.bean.platfrom.response;

import com.zhaoming.base.service.result.BaseResult;
import com.zhaoming.bean.platfrom.ServiceResponse;
import com.zhaoming.bean.platfrom.constant.ResultCode;
import com.zhaoming.bean.platfrom.constant.ResultMsg;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author huhuixin
 */
@NoArgsConstructor
public class BaseResponse extends ServiceResponse {

    public static final ServiceResponse SUCCESS = new BaseResponse(BaseResponse.Result.SUCCESS);
    public static final ServiceResponse SERVER_ERROR = new BaseResponse(BaseResponse.Result.SERVER_ERROR);

    public BaseResponse(Result result) {
        super(result.result, result.message);
    }

    public static ServiceResponse returnResult(BaseResult result) {
        switch (result) {
            case SUCCESS:
                return SUCCESS;
            default:
                return SERVER_ERROR;
        }
    }

    @AllArgsConstructor
    private enum Result {
        /**
         * 成功
         */
        SUCCESS(ResultCode.SUCCESS, ResultMsg.SUCCESS),
        /**
         * 失败
         */
        SERVER_ERROR(ResultCode.SERVER_ERROR, ResultMsg.SERVER_ERROR);
        public final String result;
        public final String message;
    }

}