package com.zhaoming.mybatis.util;

import com.github.pagehelper.PageInfo;
import com.zhaoming.base.bean.Page;

import java.util.List;

/**
 * mybatis pageHelper 与 自定义 page 转化工具类
 *
 * @author hhx
 */
public class MybatisPageHelperUtil {

    public static <T> Page<T> createPage(PageInfo<T> p, List<T> list){
        return new Page<>(p.getPageNum(), p.getSize(), p.getTotal(), list);
    }

    public static <T> Page<T> createPage(PageInfo<T> p){
        return new Page<>(p.getPageNum(), p.getSize(), p.getTotal(), p.getList());
    }
}
