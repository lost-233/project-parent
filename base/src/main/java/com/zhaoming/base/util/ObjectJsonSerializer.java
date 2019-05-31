package com.zhaoming.base.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 自定义json序列化方法(序列化object)
 * @author zm
 */
public class ObjectJsonSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        try {
            // 先使用正常json解析器解析
            gen.writeObject(value);
        }catch (Exception e){
            // 如果抛出异常放入空值,大部分都是接口文档生成时解析object类型异常
            gen.writeNull();
        }
    }
}
