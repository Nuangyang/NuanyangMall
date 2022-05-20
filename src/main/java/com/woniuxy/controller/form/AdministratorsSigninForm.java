package com.woniuxy.controller.form;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdministratorsSigninForm {
    //管理员登录
    private String adname;
    private String adpwd;
}
