package com.zhaoming.service.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoming.bean.test.dto.CategoryDto;
import com.zhaoming.service.test.entity.Category;
import com.zhaoming.service.test.mapper.CategoryMapper;
import com.zhaoming.service.test.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author system
 * @since 2019-05-16
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getList() {
        return categoryMapper.selectAll();
    }
}
