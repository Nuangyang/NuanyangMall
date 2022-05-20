package com.woniuxy.service.iofo;

import com.woniuxy.utils.exception.WoniuMallException;

public class GetShoppingException extends WoniuMallException {
    public GetShoppingException(Integer code, String message) {
        super(code, message);
    }

    //购物车商品查询异常


}
