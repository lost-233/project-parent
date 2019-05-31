package com.zhaoming.bean.platfrom.util;

import com.zhaoming.bean.platfrom.ServiceProcessor;
import com.zhaoming.bean.platfrom.ServiceResponse;
import com.zhaoming.bean.platfrom.ServiceSelector;
import com.zhaoming.bean.platfrom.constant.ServiceParameter;
import com.zhaoming.bean.platfrom.enums.Versions;
import com.zhaoming.bean.platfrom.response.CommonResponse;
import com.zhaoming.bean.platfrom.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huhuixin
 */
@Slf4j
public class ProcessorUtil {

    public static ServiceResponse processorService(Map<String, String[]> reqParams, String method, ServiceSelector selector, Versions ver) {
        if (StringUtils.isBlank(method)) {
            return new CommonResponse(CommonResult.INVALID_PARAMTER);
        }
        // 选择业务处理器
        ServiceProcessor processor = selector.select(method, ver);
        if (processor == null) {
            return new CommonResponse(CommonResult.INVALID_METHOD);
        } else if (log.isDebugEnabled()) {
            log.debug("Successfully selected processor[{}] with method : {}", processor, method);
        }
        // 获取参数
        List<ServiceParameter> serviceParams = processor.parameter();
        Map<String, String> parameters = new HashMap<>(serviceParams.size());
        if (!CollectionUtils.isEmpty(serviceParams)) {
            String[] values;
            String value;
            for (ServiceParameter param : serviceParams) {
                values = reqParams.get(param.name());
                if (values == null) {
                    if (param.isRequired()) {
                        log.error("Could not extract value with required paramter : {}", param.name());
                        // 必须参数没有传递则认为非法请求
                        return ServiceResponse.paramNotNull(param.name());
                    }
                    // 默认值
                    value = param.defaultValue();
                } else {
                    // 只取第一个值
                    value = values[0];
                    if (param.isRequired()) {
                        if (StringUtils.isBlank(value)) {
                            return ServiceResponse.paramValueNotNull(param.name());
                        } else if (!param.check().test(value)) {
                            return ServiceResponse.paramCheckFail(param.name());
                        }
                    } else {
                        if (StringUtils.isBlank(value)) {
                            value = param.defaultValue();
                        } else {
                            if (!param.check().test(value)) {
                                return ServiceResponse.paramCheckFail(param.name());
                            }
                        }
                    }
                }
                if (log.isDebugEnabled()) {
                    log.debug("Successfully extractd parameter[{}] value : {}", param.name(), value);
                }
                parameters.put(param.name(), value);
            }
        }
        // 执行业务
        try {
            return processor.process(parameters);
        } catch (Exception e) {
            log.error("Processing service [" + method + "] with processor ["
                    + processor + "] occurred an error : " + e.getMessage(), e);
        }
        return new CommonResponse(CommonResult.SERVER_ERROR);
    }

    public static Versions getMaxVersion(Collection<Versions> list) {
        return list.stream().max(Enum::compareTo).orElse(Versions.V1);
    }
}
