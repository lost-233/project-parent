package com.zhaoming.base.service.result;

/**
 * 更新或删除数据
 * @author huhuixin
 */
public enum UpdateResult {

    /**
     * 成功
     */
    SUCCESS,
    /**
     * 要修改的数据不存在
     */
    NOT_EXIST,
    /**
     * 修改失败
     */
    FAIL,
    /**
     * 失败
     */
    SERVER_ERROR
    ;
}
