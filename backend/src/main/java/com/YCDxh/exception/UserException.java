package com.YCDxh.exception;

import lombok.Getter;

public class UserException extends RuntimeException{
    @Getter
    protected Integer code;
    protected String message;


    public UserException(Integer code,String message,Throwable e) {
        super(message,e);
        this.code = code;
        this.message = message;
    }

    public UserException(Integer code,String message){
        this(code,message,null);
    }

    public void setCode(Integer code){
        this.code = code;
    }
    public void setMessage(String message){
        this.message = message;
    }

}