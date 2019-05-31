package com.zhaoming.platform.test;

import com.zhaoming.bean.platfrom.ServiceProcessor;
import com.zhaoming.bean.platfrom.annotation.Processor;
import com.zhaoming.bean.platfrom.enums.Versions;
import com.zhaoming.bean.platfrom.util.ProcessorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 业务选择器
 *
 * @author huhuixin
 * @create 2017/11/23 下午6:53
 */
@Slf4j
@Component
@ConditionalOnBean(value = {ServiceProcessor.class})
public class ServiceSelector implements ApplicationContextAware, com.zhaoming.bean.platfrom.ServiceSelector {

    /**
     * 处理器缓存
     */
    private final Map<String, Map<Versions, ServiceProcessor>> processors = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        Map<String, ServiceProcessor> beans = applicationContext.getBeansOfType(ServiceProcessor.class);
        for (ServiceProcessor p : beans.values()) {
            Processor processor = p.getClass().getAnnotation(Processor.class);
            log.info("processor '{}' load in application", (processor.method() + " : " + processor.level().getVersion()));
            Map<Versions, ServiceProcessor> processorVersionMap = processors.get(processor.method());
            if (null == processorVersionMap) {
                processorVersionMap = new HashMap<>(2);
            }
            processorVersionMap.put(processor.level(), p);
            processors.put(processor.method(), processorVersionMap);
        }
    }

    @Override
    public ServiceProcessor select(String method, Versions ver) {
        Map<Versions, ServiceProcessor> processorMap = processors.get(method);
        if (null == processorMap) {
            return null;
        }
        if (!processorMap.containsKey(ver)) {
            ver = ProcessorUtil.getMaxVersion(processorMap.keySet());
        }
        return processorMap.get(ver);
    }

    @Override
    @Autowired
    public Map<String, Map<Versions, ServiceProcessor>> getProcessors() {
        return processors;
    }
}
