package com.zhaoming.bean.platfrom.constant;

/**
 * 通用返回码
 * @author huhuixin
 */
public class ResultCode {
    /**
     * 业务处理成功
     */
    public static final String SUCCESS = "10000";


    /**
     *  -----------------       请求或系统错误返回码       --------------------
     */

    /**
     * 服务器处理异常
     */
    public static final String SERVER_ERROR = "53001";
    /**
     * 无效的签名
     */
    public static final String INVALID_SIGN = "53002";
    /**
     * 时间戳无效
     */
    public static final String INVALID_TIMESTAMP = "53003";
    /**
     * 接口方法错误
     */
    public static final String INVALID_METHOD = "53004";
    /**
     * 请求参数错误
     */
    public static final String INVALID_PARAMTER = "53005";
    /**
     * feign回调
     */
    public static final String FALLBACK = "53008";

    /**
     * 废弃的接口
     */
    public static final String DISCARD_API = "53009";
    /**
     *  -----------------      服务器已成功响应但业务错误返回值       --------------------
     */
    /**
     * 未获取到数据
     */
    public static final String NO_CONTENT = "24001";
}
