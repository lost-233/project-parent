package com.zhaoming.bean.platfrom.document;

import com.zhaoming.base.annotation.Dict;
import com.zhaoming.base.annotation.Param;
import com.zhaoming.base.util.ObjectUtil;
import com.zhaoming.bean.platfrom.ServiceProcessor;
import com.zhaoming.bean.platfrom.annotation.Processor;
import com.zhaoming.bean.platfrom.constant.ServiceParameter;
import com.zhaoming.bean.platfrom.enums.Versions;
import com.zhaoming.bean.platfrom.util.DocumentUtil;
import com.zhaoming.bean.platfrom.util.ProcessorUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author hhx
 */
@Slf4j
@Data
@NoArgsConstructor
public class PlatformDocument {
    private String description;
    private String service;
    private String method;
    private Map<Versions, DocumentVersion> versions;

    public DocumentVersion getNewDocument(){
        return versions.get(ProcessorUtil.getMaxVersion(versions.keySet()));
    }
    public String getNewVersion(){
        return ProcessorUtil.getMaxVersion(versions.keySet()).getVersion();
    }
    @SuppressWarnings("unchecked")
    public PlatformDocument(Map<Versions, ServiceProcessor> serviceProcessorMap) throws Exception {
        versions = new HashMap<>(serviceProcessorMap.size());
        serviceProcessorMap.forEach((version, serviceProcessor) -> {
            DocumentVersion documentVersion = new DocumentVersion();
            Class clazz = serviceProcessor.getClass();
            if(clazz.isAnnotationPresent(Processor.class)){
                Processor processor = (Processor) clazz.getAnnotation(Processor.class);
                documentVersion.setDescription(processor.desc());
                description = processor.desc();
                service = processor.service().getName();
                method = processor.method();
                Field[] fields = clazz.getDeclaredFields();
                if(fields.length > 0){
                    documentVersion.setParameters(new ArrayList<>(fields.length));
                }
                for(Field field :fields){
                    if(field.isAnnotationPresent(Param.class)){
                        Param param = field.getAnnotation(Param.class);
                        field.setAccessible(true);
                        try {
                            ServiceParameter serviceParameter = (ServiceParameter)field.get(clazz);
                            documentVersion.getParameters().add(new Parameter(param.desc()
                                    ,param.type().getSimpleName()
                                    ,serviceParameter.name()
                                    ,serviceParameter.isRequired()
                                    ,serviceParameter.defaultValue()));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                // 获取返回值
                Class responseClass = processor.response();
                documentVersion.setResponse(DocumentUtil.getServiceResponseClass(responseClass));
                documentVersion.setResponseJson(DocumentUtil.response2Json(responseClass));
                documentVersion.setDictionaries(DocumentUtil.getDictionaries(responseClass));
                Constructor[] constructors = responseClass.getConstructors();
                // 获取单参数的构造器 （参数为response对应的result）
                Constructor constructor = constructors[0];
                if(constructors.length > 1){
                    for(Constructor c : constructors){
                        if(c.getParameterCount() == 1){
                            constructor = c;
                        }
                    }
                }
                Class resultClass = constructor.getParameterTypes()[0];
                try {
                    Object[] results = resultClass.getEnumConstants();
                    documentVersion.setResults(new ArrayList(results.length));
                    for (Object result : results) {
                        documentVersion.getResults().add(new Result(result.toString()
                                ,ObjectUtil.getFieldValueByName(resultClass.getDeclaredField("result"),result).toString()
                                ,ObjectUtil.getFieldValueByName(resultClass.getDeclaredField("message"),result).toString()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                versions.put(version, documentVersion);
            }else{
                log.error("Please Add '@Processor' on "+clazz.getCanonicalName());
            }
        });
    }

    /** 参数列表 */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Parameter {
        private String description;
        private String type;
        private String name;
        private boolean require;
        private String defaultValue;
    }

    /**
     *返回值数据字典
     */
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dict {
        private String name;
        private String type;
        private String desc;
        /** 示例值 */
        private String value;
    }

    /** 返回状态列表 */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private String name;
        private String result;
        private String message;
    }


}

