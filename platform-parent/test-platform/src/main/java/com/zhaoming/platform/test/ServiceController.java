package com.zhaoming.platform.test;

import com.zhaoming.bean.platfrom.ServiceResponse;
import com.zhaoming.bean.platfrom.enums.Versions;
import com.zhaoming.bean.platfrom.util.ProcessorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * APP入口
 *
 * @author huhuixin
 * @create 2017/11/23 下午5:36
 */
@Controller
@ConditionalOnBean(ServiceSelector.class)
public class ServiceController {

    private static final String LEVEL_V1 = "/v*";
    private static final String LEVEL_V2 = "/v2";

    @Autowired
    private ServiceSelector selector;

    /**
     * 统一请求接入
     *
     * @param request
     * @return
     */
    @RequestMapping(value = LEVEL_V1)
    @ResponseBody
    public ServiceResponse service(HttpServletRequest request, String method) {
        Map<String, String[]> reqParams = request.getParameterMap();
        String ver = request.getRequestURI().replaceAll("/", "");
        return ProcessorUtil.processorService(reqParams, method, selector, Versions.getVersionByStr(ver));
    }

}
