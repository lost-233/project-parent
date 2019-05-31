package com.zhaoming.bean.platfrom.util;

import com.zhaoming.base.annotation.Dict;
import com.zhaoming.base.util.JsonUtil;
import com.zhaoming.base.util.ObjectUtil;
import com.zhaoming.bean.platfrom.ServiceProcessor;
import com.zhaoming.bean.platfrom.ServiceResponse;
import com.zhaoming.bean.platfrom.constant.ResultCode;
import com.zhaoming.bean.platfrom.document.DocumentInfo;
import com.zhaoming.bean.platfrom.document.GroupDocument;
import com.zhaoming.bean.platfrom.document.PlatformDocument;
import com.zhaoming.bean.platfrom.enums.Service;
import com.zhaoming.bean.platfrom.enums.Versions;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huhuixin
 */
@Slf4j
public class DocumentUtil {
    /**
     * 服务器部署正式环境的IP
     */
    public static final String IP = "172.16.1.22";
    /**
     * 递归属性标识只生成一次
     */
    private static boolean flag=false;

    public static String response2Json(Class<? extends ServiceResponse> clazz) {
        try {
            ServiceResponse response = (ServiceResponse) JsonUtil.getInstance(clazz);
            if(response == null){
                log.warn("clazz {} get instance fail!", clazz.getName());
                response = new ServiceResponse();
            }
            JsonUtil.setValue(response);
            response.setResult(ResultCode.SUCCESS);
            response.setMessage("请求成功");
            return JsonUtil.toJson(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{}";
    }

    public static String getServiceResponseClass(Class<? extends ServiceResponse> clazz){
        return clazz.getCanonicalName();
    }

    /**
     * 遍历class的所有带Dict注解的属性
     * @param responseClass 返回值类型
     * @return
     */
    public static List<PlatformDocument.Dict> getDictionaries(Class responseClass){
        List<PlatformDocument.Dict> list = new ArrayList<>();
        try {
            collect(list,responseClass);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    private static void addDict(List<PlatformDocument.Dict> list,Field field){
        if(field != null){
            if(field.isAccessible()){
                field.setAccessible(true);
            }
            Dict dict = field.getAnnotation(Dict.class);
            if (dict != null){
                list.add(new PlatformDocument.Dict(field.getName()
                        , field.getType().getSimpleName()
                        , dict.desc(), dict.value()));
            }
        }
    }

    private static void collect(List<PlatformDocument.Dict> list,Class clazz) throws Exception {
        Method[] methods = clazz.getMethods();
        for (Method method : methods){
            if(method.getName().startsWith("set")){
                if(method.getParameterTypes().length == 0){
                    break;
                }
                Class paramClazz = method.getParameterTypes()[0];
                Field field = ObjectUtil.getFieldByClass(clazz,ObjectUtil.getFieldNameByMethodName(method.getName()));
                if(field == null){
                    continue;
                }
                addDict(list,field);
                if (!ObjectUtil.isBaseType(paramClazz)) {
                    if(Collection.class.isAssignableFrom(paramClazz)){
                        // 集合类型 , 取泛型
                        Type genericType = field.getGenericType();
                        if (genericType != null) {
                            if(genericType instanceof ParameterizedType){
                                Class<?> genericClazz = ObjectUtil.getFirstGenericClazz(genericType);
                                if(!genericClazz.equals(clazz)){
                                    collect(list, genericClazz);
                                }else if (!flag) {
                                    flag=true;
                                    collect(list, genericClazz);
                                }
                            }
                        }  // 没有泛型
                    } else if(Map.class.isAssignableFrom(paramClazz)){
                        Type genericType = field.getGenericType();
                        if (genericType != null) {
                            if(genericType instanceof ParameterizedType) {
                                try {
                                    Type[] arguments = ((ParameterizedType) genericType).getActualTypeArguments();
                                    collect(list,((Class<?>) arguments[1]));
                                } catch (Exception e) {
                                    log.error(e.getMessage());
                                }
                            }
                        }
                    }else{
                        try {
                            collect(list,paramClazz);
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    public static DocumentInfo createDocument(String address, String profiles, Map<String, Map<Versions, ServiceProcessor>> processorMap) {
        Set<String> processorKeys = processorMap.keySet();
        List<PlatformDocument> list = processorKeys
                .stream().map(key -> {
                    try {
                        return new PlatformDocument(processorMap.get(key));
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
        Map<String, List<PlatformDocument>> map = list.stream().collect(Collectors.groupingBy(PlatformDocument :: getService));
        List<GroupDocument> groupDocuments = new ArrayList<>(Service.values().length);
        for(Service service : Service.values()){
            if(map.containsKey(service.getName())){
                GroupDocument groupDocument = new GroupDocument();
                groupDocument.setDocuments(map.get(service.getName()));
                groupDocument.setServiceName(service.getName());
                groupDocuments.add(groupDocument);
            }
        }
        return new DocumentInfo().setAddress(address).setProfiles(profiles).setList(groupDocuments);
    }
}
