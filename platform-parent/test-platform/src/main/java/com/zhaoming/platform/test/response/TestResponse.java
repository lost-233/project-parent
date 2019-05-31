package com.zhaoming.platform.test.response;

import com.zhaoming.base.annotation.Dict;
import com.zhaoming.bean.platfrom.ServiceResponse;
import com.zhaoming.bean.platfrom.constant.ResultCode;
import com.zhaoming.bean.platfrom.constant.ResultMsg;
import com.zhaoming.bean.test.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 控制器
 * @author zm
 * @date 2018-12-05 15:43:51
 */
@Data
@NoArgsConstructor
public class TestResponse extends ServiceResponse {

    @Dict("数据")
    private List<CategoryDto> data;

    public TestResponse(Result result){
        super(result.result, result.message);
    }

    public TestResponse(Result result, List<CategoryDto> data){
        super(result.result, result.message);
        this.data = data;
    }

    @Getter
    @AllArgsConstructor
    public enum Result {
        SUCCESS (ResultCode.SUCCESS, ResultMsg.SUCCESS),
        SERVER_ERROR (ResultCode.SERVER_ERROR, ResultMsg.SERVER_ERROR),
        NO_CONTENT (ResultCode.NO_CONTENT, "未获取到内容"),
        ;
        public String result;
        public String message;
    }
}