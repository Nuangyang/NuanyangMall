package com.woniuxy.utils.exception;

public class EmailException extends WoniuMallException {

    //电子邮箱异常
    public EmailException(Integer code, String message) {
        super(code, message);
    }
}
