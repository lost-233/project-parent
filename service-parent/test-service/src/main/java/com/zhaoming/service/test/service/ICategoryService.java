package com.zhaoming.service.test.service;

import com.zhaoming.bean.test.dto.CategoryDto;
import com.zhaoming.service.test.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author system
 * @since 2019-05-16
 */
public interface ICategoryService extends IService<Category> {
    /**
     * test
     * @return
     */
    List<CategoryDto> getList();
}
