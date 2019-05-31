package com.zhaoming.mybatis.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhaoming.base.bean.Page;
import com.zhaoming.base.bean.PageCondition;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * mybatisPlus 自带page 与 自定义 page 转化工具类
 *
 * @author hhx
 */
public class MybatisPlusPageUtil {

    @SuppressWarnings("unchecked")
    public static <E, T> Page<T> convertPage(IPage<E> p, Function<E, T> convert){
        if(CollectionUtils.isNotEmpty(p.getRecords())){
            List<T> list = p.getRecords().stream().map(convert)
                    .collect(Collectors.toList());
            return new Page<>(p.getCurrent(), p.getSize(), p.getTotal(), list);
        }
        return new Page<>(p.getCurrent(), p.getSize(), p.getTotal(), Collections.EMPTY_LIST);
    }

    public static <T> Page<T> createPage(IPage<?> p, List<T> list){
        return new Page<>(p.getCurrent(), p.getSize(), p.getTotal(), list);
    }

    public static <T> Page<T> createPage(IPage<T> p){
        return new Page<>(p.getCurrent(), p.getSize(), p.getTotal(), p.getRecords());
    }

    /**
     * 查询数据范围
     * @param condition
     * @return
     */
    public static IPage limit(PageCondition condition){
        return new com.baomidou.mybatisplus.extension.plugins.pagination.Page(condition.getPageNum(), condition.getPageSize());
    }
}
