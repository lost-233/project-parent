package com.zhaoming.web.manage.vo;


import com.zhaoming.base.constant.CommonConstant;

/**
 * 可检查的
 *
 * @author hhx
 */
public interface CheckParamAble {

    String SUCCESS = CommonConstant.SUCCESS;

    /**
     * 是否通过检查
     * @return
     */
    String check();
}
