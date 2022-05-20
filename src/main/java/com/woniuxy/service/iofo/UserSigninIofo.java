package com.woniuxy.service.iofo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Properties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSigninIofo {

    private String uname;  //用户名
    private String upwd;    //密码
    private Integer state;   //用户状态
}
