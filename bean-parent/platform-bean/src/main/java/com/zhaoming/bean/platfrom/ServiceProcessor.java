package com.zhaoming.bean.platfrom;


import com.zhaoming.bean.platfrom.constant.ServiceParameter;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 任务处理器抽象接口
 *
 * @author huhuixin
 * @date 2017/11/23
 */
public interface ServiceProcessor {

    /**
     * 返回该业务处理器处理所需的参数
     *
     * @return
     */
    List<ServiceParameter> parameter();

    /**
     * 处理业务
     * @param parameters
     * @throws ParseException
     * @return
     */
    ServiceResponse process(Map<String, String> parameters) throws ParseException;
}
