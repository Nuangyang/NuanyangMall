package com.woniuxy.utils.exception;

public class AddtoException extends  WoniuMallException{

    //添加失败
    public AddtoException(Integer code, String message) {
        super(code, message);
    }
}
