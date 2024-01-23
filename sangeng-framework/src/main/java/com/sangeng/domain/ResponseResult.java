package com.sangeng.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sangeng.enums.AppHttpCodeEnum;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public ResponseResult() {
        this.code = AppHttpCodeEnum.SUCCESS.getCode();
        this.msg = AppHttpCodeEnum.SUCCESS.getMsg();
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseResult errorResult(int code, String msg){
        ResponseResult result = new ResponseResult();
        return result.error(code, msg);
    }

    public static ResponseResult okResult(int code, String msg){
        ResponseResult result = new ResponseResult();
        return result.ok(code, null, msg);
    }

    public static ResponseResult okResult(){
        ResponseResult responseResult = new ResponseResult();
        return responseResult;
    }

    public static ResponseResult okResult(Object data){
        ResponseResult result = setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS,AppHttpCodeEnum.SUCCESS.getMsg());
        if(data != null){
            result.setData(data);
        }
        return result;
    }

    public static ResponseResult errorResult(AppHttpCodeEnum appHttpCodeEnum){
        return setAppHttpCodeEnum(appHttpCodeEnum, appHttpCodeEnum.getMsg());
    }

    public static ResponseResult errorResult(String msg, AppHttpCodeEnum appHttpCodeEnum){
        return setAppHttpCodeEnum(appHttpCodeEnum, msg);
    }

    public static ResponseResult setAppHttpCodeEnum(AppHttpCodeEnum appHttpCodeEnum){
        return okResult(appHttpCodeEnum.getCode(),appHttpCodeEnum.getMsg());
    }

    private static ResponseResult setAppHttpCodeEnum(AppHttpCodeEnum appHttpCodeEnum, String msg){
        return okResult(appHttpCodeEnum.getCode(),msg);
    }

    public ResponseResult<?> error(Integer code, String msg){
        this.code = code;
        this.msg = msg;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data){
        this.code = code;
        this.data = data;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data, String msg){
        this.code = code;
        this.data =data;
        this.msg = msg;
        return this;
    }

    public ResponseResult<?> ok(T data){
        this.data = data;
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
