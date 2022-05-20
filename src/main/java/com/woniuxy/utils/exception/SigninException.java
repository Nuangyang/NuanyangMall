package com.woniuxy.utils.exception;

public class SigninException extends  WoniuMallException {

    //登录异常
    public SigninException(Integer code, String message) {
        super(code, message);
    }
}
