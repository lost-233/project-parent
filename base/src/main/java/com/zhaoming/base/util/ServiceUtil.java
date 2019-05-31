package com.zhaoming.base.util;


import com.zhaoming.base.bean.Page;
import com.zhaoming.base.constant.CommonConstant;
import com.zhaoming.base.service.ServiceResult;
import com.zhaoming.base.service.result.ContentResult;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 服务工具类, 处理返回值
 * @author hhx
 */
public class ServiceUtil {

    /**
     * 请求失败则抛出异常
     * @param result
     * @param <D>
     * @return
     */
    public static <E extends Enum, D> D requireGetResultData(ServiceResult<E, D> result){
        return getResultData(result).orElseThrow(() -> new RuntimeException("访问服务失败!"));
    }

    /**
     * 根据结果是否成功返回不同的值 执行给定的函数
     * @param result
     * @param success
     * @param fail
     * @param <R>
     * @param <D>
     * @return
     */
    public static <E extends Enum, R, D> R returnResult(ServiceResult<E, D> result,
                                        Supplier<R> success,
                                        Supplier<R> fail){
        return returnResult(result, d -> true, success, fail);
    }

    /**
     * 消费这个返回值,根据成功或者失败 以及对result的处理结果 执行给定的函数
     * @param result
     * @param predicate
     * @param <R>
     * @param <D>
     * @return
     */
    public static <E extends Enum, R, D> R returnResult(ServiceResult<E, D> result,
                                        Predicate<D> predicate,
                                        Supplier<R> success,
                                        Supplier<R> fail){
        if(result.getResult().name().equals(CommonConstant.SUCCESS)){
            if(predicate.test(result.getData())){
                return success.get();
            }
        }
        return fail.get();
    }

    /**
     * 消费这个返回值,如果失败抛出异常
     * @param result
     * @param <D> data
     * @return
     */
    public static <E extends Enum, D> void consumerData(ServiceResult<E, D> result, Consumer<D> consumer){
        consumer.accept(requireGetResultData(result));
    }

    /**
     * 请求失败返回空值
     * @param result
     * @param <D>
     * @return
     */
    public static <E extends Enum, D> Optional<D> getResultData(ServiceResult<E, D> result) {
        if (result.getResult().name().equals(CommonConstant.SUCCESS)) {
            return Optional.of(result.getData());
        }
        return Optional.empty();
    }

    /**
     * 自定义对返回值的处理
     * @param result
     * @param function
     * @param <E>
     * @param <R>
     * @return
     */
    public static <E extends Enum, R> R returnResult(ServiceResult<E, ?> result, Function<E, R> function) {
        return function.apply(result.getResult());
    }

    /**
     * 获取page
     * @param pageResult
     * @param <E>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <E> Page<E> getPage(ServiceResult<ContentResult, Page<E>> pageResult){
        if(pageResult.getResult().equals(ContentResult.SUCCESS)){
            return pageResult.getData();
        }else{
            return Page.EMPTY_PAGE;
        }
    }
}
