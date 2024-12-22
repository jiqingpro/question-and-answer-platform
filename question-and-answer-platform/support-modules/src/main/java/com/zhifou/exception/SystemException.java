package com.zhifou.exception;

import com.zhifou.common.AppHttpCodeEnum;
import lombok.Getter;

@Getter
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}

