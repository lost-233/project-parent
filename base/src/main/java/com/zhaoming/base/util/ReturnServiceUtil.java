package com.zhaoming.base.util;

import com.zhaoming.base.dto.Convert;
import com.zhaoming.base.service.ServiceResult;
import com.zhaoming.base.service.result.BaseResult;
import com.zhaoming.base.service.result.ContentResult;
import com.zhaoming.base.service.result.UpdateResult;
import com.zhaoming.base.util.function.ExecuteInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 服务工具类
 * @author hhx
 */
@Slf4j
public class ReturnServiceUtil {

    public static final ServiceResult SUCCESS = new ServiceResult(BaseResult.SUCCESS);
    public static final ServiceResult SERVER_ERROR = new ServiceResult(BaseResult.SERVER_ERROR);
    /**
     * 无内容
     * ServiceResult<ContentResult, ?>
     */
    public static final ServiceResult NO_CONTENT = new ServiceResult(ContentResult.NO_CONTENT);
    /**
     * 修改失败
     * ServiceResult<UpdateResult, ?>
     */
    public static final ServiceResult FAIL = new ServiceResult(UpdateResult.FAIL);
    /**
     * 要修改的数据不存在
     * ServiceResult<UpdateResult, ?>
     */
    public static final ServiceResult NOT_EXIST = new ServiceResult(UpdateResult.NOT_EXIST);

    public static <D> ServiceResult<ContentResult, D> nonNull(D d){
        return returnNoContent(Objects::isNull, d);
    }

    public static ServiceResult<ContentResult, String> stringNotEmpty(String data){
        return returnNoContent(StringUtils::isBlank, data);
    }

    public static <D> ServiceResult<ContentResult, List<D>> listNotEmpty(List<D> list){
        return returnNoContent(CollectionUtils::isEmpty, list);
    }

    @SuppressWarnings("unchecked")
    public static <E, D> ServiceResult<ContentResult, D> convertObject(E e, Function<E, D> convert){
        if(e == null){
            return NO_CONTENT;
        }
        return new ServiceResult<>(ContentResult.SUCCESS, convert.apply(e));
    }

    public static <E, D> ServiceResult<ContentResult, D> convertObject2Dto(E e, Convert<E, D> convert){
        return convertObject(e, convert::entity2dto);
    }

    @SuppressWarnings("unchecked")
    public static <E, D> ServiceResult<ContentResult, List<D>> convertList(List<E> list, Function<E, D> convert){
        if(CollectionUtils.isEmpty(list)){
            return NO_CONTENT;
        }
        return new ServiceResult<>(ContentResult.SUCCESS,
                list.stream().map(convert).collect(Collectors.toList()));
    }

    public static <E, D> ServiceResult<ContentResult, List<D>> convertList2Dto(List<E> list, Convert<E, D> convert){
        return convertList(list, convert::entity2dto);
    }

    @SuppressWarnings("unchecked")
    public static <D> ServiceResult<ContentResult, D> returnNoContent(Predicate<D> isNoContent, D data){
        if(isNoContent.test(data)){
            return NO_CONTENT;
        }else {
            return new ServiceResult<>(ContentResult.SUCCESS, data);
        }
    }

    @SuppressWarnings("unchecked")
    public static <D> ServiceResult<BaseResult, D> returnSuccess(Supplier<D> getData){
        D data = getData.get();
        if(data != null){
            return new ServiceResult<>(BaseResult.SUCCESS, data);
        }
        return (ServiceResult<BaseResult, D>) SERVER_ERROR;
    }

    /**
     * 适用于返回 UpdateResult 的 方法
     * @return
     */
    @SuppressWarnings("unchecked")
    public static ServiceResult<UpdateResult, ?> update(boolean isSuccess){
        if(isSuccess){
            return SUCCESS;
        }
        return FAIL;
    }

    @SuppressWarnings("unchecked")
    public static <D> ServiceResult<UpdateResult, ?> update(D data, Function<D, Boolean> updateAction){
        if(data == null){
            return NOT_EXIST;
        }
        return update(updateAction.apply(data));
    }

    @SuppressWarnings("unchecked")
    public static ServiceResult<BaseResult, ?> executeCatchException(ExecuteInterface action){
        try {
            action.execute();
            return SUCCESS;
        } catch (Exception e) {
            return SERVER_ERROR;
        }
    }

    @SuppressWarnings("unchecked")
    public static ServiceResult<BaseResult, ?> executeSuccess(boolean isSuccess){
        return isSuccess ? SUCCESS : SERVER_ERROR;
    }
}
