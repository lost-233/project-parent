package com.zhaoming.base.service;

/**
 * 服务类通用返回类型
 *
 * @author huhuixin
 * @create 2017/11/23 上午10:11
 */

public class ServiceResult<R, D> {

    private R result;
    private D data;

    public ServiceResult() {
    }

    public ServiceResult(R result, D data) {
        this.result = result;
        this.data = data;
    }

    public ServiceResult(R result) {
        this(result, null);
    }

    public R getResult() {
        return this.result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    public D getData() {
        return this.data;
    }

    public void setData(D data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ServiceResult [result=" + this.result + ", data=" + this.data + "]";
    }
}
