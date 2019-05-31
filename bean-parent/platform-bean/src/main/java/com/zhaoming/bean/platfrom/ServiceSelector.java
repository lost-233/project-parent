package com.zhaoming.bean.platfrom;

import com.zhaoming.bean.platfrom.enums.Versions;

import java.util.Map;

/**
 * 任务选择器
 *
 * @author huhuixin
 * @date 2017/11/23
 */
public interface ServiceSelector {

    /**
     * 根据业务代码和版本选择业务处理器
     *
     * @param method 接口标识
     * @param ver 版本
     * @return 处理器
     */
    ServiceProcessor select(String method, Versions ver);

    /**
     * 获取所有处理器
     * @return 所有处理器
     */
    Map<String, Map<Versions, ServiceProcessor>> getProcessors();
}
