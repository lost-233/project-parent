package com.zhaoming.bean.test.result;

/**
 * @author zm
 */
public interface CategoryResult {
    enum Result{
        /**
         * 成功
         */
        SUCCESS,
        /**
         * 无内容
         */
        NO_CONTENT,
        /**
         * 服务器错误
         */
        SERVER_ERROR,
        ;
    }
}
