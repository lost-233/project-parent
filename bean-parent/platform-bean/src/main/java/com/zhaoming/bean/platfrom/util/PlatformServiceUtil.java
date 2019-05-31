package com.zhaoming.bean.platfrom.util;

import com.zhaoming.base.constant.CommonConstant;
import com.zhaoming.base.service.ServiceResult;
import com.zhaoming.base.util.ServiceUtil;
import com.zhaoming.bean.platfrom.ServiceResponse;
import com.zhaoming.bean.platfrom.response.BaseResponse;
import com.zhaoming.bean.platfrom.response.ContentResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 处理返回值工具
 *
 * @author hhx
 */
@Slf4j
public class PlatformServiceUtil {

    /**
     * 服务层返回值为集合时,如果列表为空 返回 ContentResponse.NO_CONTENT
     *
     * @param result
     * @return
     */
    public static ServiceResponse collectionNotEmpty(ServiceResult<? extends Enum, ? extends Collection> result) {
        return collectionNotEmpty(result, () -> ContentResponse.NO_CONTENT);
    }

    /**
     * 服务层返回值为集合时,自定义列表为空的返回值
     *
     * @param result
     * @param collectionEmpty 数据列表为空的返回值
     * @return
     */
    public static ServiceResponse collectionNotEmpty(ServiceResult<? extends Enum, ? extends Collection> result, Supplier<ServiceResponse> collectionEmpty) {
        return ServiceUtil.returnResult(result, CollectionUtils::isEmpty,
                () -> new ContentResponse(result.getData()), collectionEmpty);
    }

    /**
     * 服务层返回值为集合时,如果返回的对象为空
     *
     * @param result
     * @return
     */
    public static ServiceResponse objectNonNull(ServiceResult<? extends Enum, ?> result) {
        return objectNonNull(result, () -> BaseResponse.SERVER_ERROR);
    }

    /**
     * 服务层返回值为集合时,如果返回的对象为空
     *
     * @param result
     * @param objectNull 当object为空时的返回值
     * @return
     */
    public static ServiceResponse objectNonNull(ServiceResult<? extends Enum, ?> result, Supplier<ServiceResponse> objectNull) {
        return ServiceUtil.returnResult(result, Objects::nonNull, () -> new ContentResponse(result.getData()), objectNull);
    }

    /**
     * 服务层返回值为集合时,result 不是 success 则返回
     *
     * @param result
     * @return
     */
    public static ServiceResponse base(ServiceResult<? extends Enum, ?> result) {
        return ServiceUtil.returnResult(result,
                () -> BaseResponse.SUCCESS,
                () -> BaseResponse.SERVER_ERROR);
    }

    /**
     * 自定义处理
     *
     * @param result   服务层返回的值
     * @param function 自定义对接口返回值的处理方法
     * @return
     */
    public static <E extends Enum> ServiceResponse returnResult(ServiceResult<E, ?> result, Function<E, ServiceResponse> function) {
        return ServiceUtil.returnResult(result, function);
    }


    /**
     * 根据成功失败返回对应结果
     *
     * @param result
     * @param success
     * @param fail
     * @param <E>
     * @return
     */
    public static <E extends Enum> ServiceResponse returnResult(ServiceResult<E, ?> result, Supplier<ServiceResponse> success, Supplier<ServiceResponse> fail) {
        return ServiceUtil.returnResult(result, success, fail);
    }


    /**
     * 通过service层返回的枚举找到名称相同的枚举对象构造response并返回
     *
     * @param result              服务层返回的值
     * @param responseResultClass response 枚举, 其中的枚举值名称必须与 service中返回的枚举相同
     * @param factory             通过response枚举和result的返回值构造最终返回值
     * @return
     */
    public static <D, E extends Enum> ServiceResponse returnResult(ServiceResult<? extends Enum, D> result,
                                                                   Class<E> responseResultClass,
                                                                   BiFunction<E, Optional<D>, ServiceResponse> factory) {
        try {
            // 获取与serviceResultEnum名称相同的responseEnum实例
            E e = (E) Enum.valueOf(responseResultClass, result.getResult().name());
            if (e.name().equals(CommonConstant.SUCCESS)) {
                return factory.apply(e, result.getData() == null ? Optional.empty() : Optional.of(result.getData()));
            }
            return factory.apply(e, Optional.empty());
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return BaseResponse.SERVER_ERROR;
        }
    }
}
