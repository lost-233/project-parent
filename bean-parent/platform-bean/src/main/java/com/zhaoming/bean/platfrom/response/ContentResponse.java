package com.zhaoming.bean.platfrom.response;

import com.zhaoming.base.annotation.Dict;
import com.zhaoming.base.service.result.ContentResult;
import com.zhaoming.bean.platfrom.ServiceResponse;
import com.zhaoming.bean.platfrom.constant.ResultCode;
import com.zhaoming.bean.platfrom.constant.ResultMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author huhuixin
 */
@NoArgsConstructor
public class ContentResponse extends ServiceResponse {

    public static final ServiceResponse NO_CONTENT = new ContentResponse(Result.NO_CONTENT);
    public static final ServiceResponse SERVER_ERROR = new ContentResponse(Result.SERVER_ERROR);

    @Getter
    @Dict(desc="返回的数据")
    private Object content;

    public ContentResponse(Result result) {
        super(result.result, result.message);
    }

    public ContentResponse(Result result, Object content) {
        super(result.result, result.message);
        this.content = content;
    }

    public ContentResponse(Object object) {
        super(Result.SUCCESS.result, Result.SUCCESS.message);
        content = object;
    }

    public static ServiceResponse returnResult(ContentResult result, Object content) {
        switch (result) {
            case SUCCESS:
                return new ContentResponse(Result.SUCCESS, content);
            case NO_CONTENT:
                return NO_CONTENT;
            default:
                return SERVER_ERROR;
        }
    }

    @AllArgsConstructor
    public enum Result {
        /**
         * 成功
         */
        SUCCESS(ResultCode.SUCCESS, ResultMsg.SUCCESS),
        /**
         * 失败
         */
        SERVER_ERROR(ResultCode.SERVER_ERROR, ResultMsg.SERVER_ERROR),
        /**
         * 无内容
         */
        NO_CONTENT(ResultCode.NO_CONTENT, "未获取到内容");
        public final String result;
        public final String message;
    }
}
