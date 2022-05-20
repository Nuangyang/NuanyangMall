package com.woniuxy.utils.exception;

public class CategoryException extends WoniuMallException {

    //分类查询异常
    public CategoryException(Integer code, String message) {
        super(code, message);
    }
}
