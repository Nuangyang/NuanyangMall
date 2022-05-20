package com.woniuxy.utils.exception;

import lombok.Data;

/**
 * 项目中需要提供一个异常总类，然后在项目的不同业务处理中针对不同的业务处理失败的情况，抛出不同的异常
 */
@Data
public class WoniuMallException extends Exception{
    private Integer code;//异常状态码
    public WoniuMallException(Integer code,String message){
        super(message);
        this.code=code;
    }
}
