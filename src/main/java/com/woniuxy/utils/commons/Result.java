package com.woniuxy.utils.commons;

import lombok.Data;

@Data
public class Result<T> {

    private Boolean flag;//用于标记当前请求是否成功

    private Integer code;//响应状态码

    private String message;//用于描述当前操作的信息

    private T data;//用于设置给前端返回的真实数据


    public static Result success(Integer code,String message,Object data){
        Result result = new Result();
        result.setFlag(true);
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result fail(Integer code,String message){
        Result result = new Result();
        result.setFlag(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

}
