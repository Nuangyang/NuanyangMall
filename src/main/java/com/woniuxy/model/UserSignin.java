package com.woniuxy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignin {
    private Integer usid;//id
    private String uname;//用户名
    private String upwd;//密码
    private Integer state;//用户状态
}
