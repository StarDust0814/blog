package com.sangeng.exception;

import com.sangeng.enums.AppHttpCodeEnum;

public class SystemException extends RuntimeException {
    // 系统异常
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum){
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg  = httpCodeEnum.getMsg();
    }
}
