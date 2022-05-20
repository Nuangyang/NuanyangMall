package com.woniuxy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer uid;
    private String uname;   //账户名a
    private String upwd;    //密码
    private String uemail;   //电子邮箱
    private String uage;    //年龄
    private String birthday; //生日
    private String uphone;  //手机号
    private String address; //默认收货地址
    private Integer state; //用户状态
    private String head;//用户头像
}
