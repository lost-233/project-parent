package com.zhaoming.web.manage.utils;


import com.zhaoming.base.service.ServiceResult;
import com.zhaoming.base.service.result.BaseResult;
import com.zhaoming.base.service.result.ContentResult;
import com.zhaoming.base.service.result.InsertResult;
import com.zhaoming.base.service.result.UpdateResult;
import com.zhaoming.base.util.ServiceUtil;
import com.zhaoming.bean.web.Result;

/**
 * 处理service
 *
 * @author hhx
 */
@SuppressWarnings("unchecked")
public class WebServiceUtil {

    public static <D> Result<D> returnResult(ServiceResult<? extends Enum, D> result) {
        return ServiceUtil.returnResult(result,
                () -> Result.OK,
                () -> result.getData() == null ? Result.ERROR : Result.error(result.getData().toString()));
    }

    public static <D> Result<D> returnContent(ServiceResult<ContentResult, D> result) {
        if (result.getResult().name().equals(BaseResult.SERVER_ERROR.name())){
            return Result.error(result.getData().toString());
        }
        if (result.getResult().equals(ContentResult.NO_CONTENT)){
            return Result.NO_CONTENT;
        }
        return ServiceUtil.returnResult(result,
                () -> Result.ok(result.getData()),
                () -> Result.ERROR);
    }

    public static <D> Result<D> returnUpdate(ServiceResult<UpdateResult, D> result) {
        if (result.getResult().name().equals(BaseResult.SERVER_ERROR.name())){
            return Result.error(result.getData().toString());
        }
        if (result.getResult().equals(UpdateResult.FAIL)){
            return Result.error("更新失败!");
        }else if (result.getResult().equals(UpdateResult.NOT_EXIST)){
            return Result.error("未找到更新目标!");
        }
        return ServiceUtil.returnResult(result,
                () -> Result.ok(result.getData()),
                () -> Result.ERROR);
    }

    public static <D> Result<D> returnInsert(ServiceResult<InsertResult, D> result) {
        if (result.getResult().name().equals(BaseResult.SERVER_ERROR.name())){
            return Result.error(result.getData().toString());
        }
        if (result.getResult().equals(InsertResult.DUPLICATE)){
            return Result.error("重复插入!");
        }
        return ServiceUtil.returnResult(result,
                () -> Result.ok(result.getData()),
                () -> Result.ERROR);
    }
}
