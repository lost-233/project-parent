package com.zhaoming.client.test;

import com.zhaoming.base.service.ServiceResult;
import com.zhaoming.bean.test.dto.CategoryDto;
import com.zhaoming.bean.test.result.CategoryResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author zm
 */
@FeignClient(TestConstant.CLIENT_NAME)
public interface CategoryClient {
    /**
     * 列表
     * @return 测试数据
     */
    @GetMapping("/category")
    ServiceResult<CategoryResult.Result, List<CategoryDto>> list();
}
