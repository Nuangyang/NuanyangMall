package com.woniuxy.utils.exception;

public class PassworderrorException extends WoniuMallException {

    //密码错误异常
    public PassworderrorException(Integer code, String message) {
        super(code, message);
    }
}
