package com.woniuxy.controller.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSigninForm {

    private String uname; //用户名
    private String upwd;//用户密码
    private Integer state;//用户状态

}
