package com.woniuxy.utils.exception;

public class GetGoodsByIdException extends WoniuMallException {

    //通过ID查询商品
    public GetGoodsByIdException(Integer code, String message) {
        super(code, message);
    }
}
