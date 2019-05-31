package com.zhaoming.base.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * @author zm
 */
public class CopyUtils {
    /**
     * 复制对象
     * @param source 源
     * @param dest 目标
     * @throws Exception
     */
    public static void copy(Object source, Object dest) throws Exception {
        // 获取属性
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(),Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

        BeanInfo destBean = Introspector.getBeanInfo(dest.getClass(),Object.class);
        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();

        try {
            for (PropertyDescriptor aSourceProperty : sourceProperty) {

                for (PropertyDescriptor aDestProperty : destProperty) {

                    if (aSourceProperty.getName().equals(aDestProperty.getName()) && aSourceProperty.getPropertyType() == aDestProperty.getPropertyType()) {
                        // 调用source的getter方法和dest的setter方法
                        aDestProperty.getWriteMethod().invoke(dest, aSourceProperty.getReadMethod().invoke(source));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("属性复制失败:" + e.getMessage());
        }
    }
}
