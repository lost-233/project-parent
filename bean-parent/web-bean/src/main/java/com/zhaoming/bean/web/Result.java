package com.zhaoming.bean.web;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * Ajax请求返回值
 *
 * @author huhuixin
 * @create 2017/11/25 上午10:57
 */

public class Result<T> {
    private Integer status;
    private String message;
    private T data;

    public Result() {
    }

    private Result(String message) {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.message = message;
    }

    private Result(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    private Result(T data) {
        this.status = HttpStatus.OK.value();
        this.message = HttpStatus.OK.name();
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk(){
        return status == HttpStatus.OK.value();
    }

    public static final Result OK = new Result(HttpStatus.OK.value(),HttpStatus.OK.name());
    public static final Result ERROR = new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR.name());
    public static final Result NO_CONTENT = new Result(HttpStatus.NO_CONTENT.value(),HttpStatus.NO_CONTENT.name());
    public static final Result ALREADY_REPORTED = new Result(HttpStatus.ALREADY_REPORTED.value(),HttpStatus.ALREADY_REPORTED.name());
    public static final Result EN_EMPTY = new Result(506,"en_empty");

    public static <T> Result<T> ok(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> error(String message) {
        return new Result<T>(message);
    }

    public static <T> Result<T> build(T t) {
        if (t != null){
            return ok(t);
        }else{
            return NO_CONTENT;
        }
    }

    public static <T> Result<T> build(boolean flag) {
        if (flag){
            return OK;
        }else{
            return ERROR;
        }
    }

    public static <T> Result<T> build(Collection collection) {
        if (!CollectionUtils.isEmpty(collection)){
            return ok((T) collection);
        }else{
            return NO_CONTENT;
        }
    }
}

