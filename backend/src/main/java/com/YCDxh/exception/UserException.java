package com.YCDxh.exception;

import com.YCDxh.model.enums.ResponseCode;
import lombok.Getter;

public class UserException extends RuntimeException {
    @Getter
    protected int code;
    @Getter
    protected String message;


    public UserException(int code, String message, Throwable e) {
        super(message, e);
        this.code = code;
        this.message = message;
    }

    // 通过 ResponseCode 初始化
    public UserException(ResponseCode code) {
        super(code.getMessage());
        this.code = code.getCode();
    }

    public UserException(int code, String message) {
        super(message);
        this.code = code;
    }


}