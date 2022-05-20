package com.woniuxy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdministraTors {

    private Integer bsid;   //id
    private String adname;  //管理员用户名
    private String adpwd;   //管理员密码
    private String jurisdiction;//管理员权限



}
