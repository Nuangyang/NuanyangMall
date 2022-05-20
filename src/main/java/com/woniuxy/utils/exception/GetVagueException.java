package com.woniuxy.utils.exception;

public class GetVagueException extends WoniuMallException {

    //模糊查询异常
    public GetVagueException(Integer code, String message) {
        super(code, message);
    }
}
