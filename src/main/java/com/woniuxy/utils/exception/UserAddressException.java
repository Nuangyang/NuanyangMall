package com.woniuxy.utils.exception;

public class UserAddressException extends  WoniuMallException {
    public UserAddressException(Integer code, String message) {
        super(code, message);
    }

    //地址查询异常
}
