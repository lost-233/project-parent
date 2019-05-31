package com.zhaoming.service.test.mapper;

import com.zhaoming.bean.test.dto.CategoryDto;
import com.zhaoming.service.test.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author system
 * @since 2019-05-16
 */
@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * test
     * @return
     */
    @Select("select * from category where layer = 0 ")
    List<CategoryDto> selectAll();
}
