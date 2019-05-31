package com.zhaoming.service.test.controller;


import com.zhaoming.base.service.ServiceResult;
import com.zhaoming.bean.test.dto.CategoryDto;
import com.zhaoming.bean.test.result.CategoryResult;
import com.zhaoming.client.test.CategoryClient;
import com.zhaoming.service.test.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author system
 * @since 2019-05-16
 */
@RestController
public class CategoryController implements CategoryClient {

    @Autowired
    ICategoryService categoryService;

    @Override
    @GetMapping("/category")
    public ServiceResult<CategoryResult.Result, List<CategoryDto>> list() {
        List<CategoryDto> list = categoryService.getList();
        return new ServiceResult(CategoryResult.Result.SUCCESS, list);
    }
}
