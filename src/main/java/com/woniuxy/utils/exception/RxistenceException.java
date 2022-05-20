package com.woniuxy.utils.exception;

public class RxistenceException extends WoniuMallException {

    //存在异常
    public RxistenceException(Integer code, String message) {
        super(code, message);
    }
}
