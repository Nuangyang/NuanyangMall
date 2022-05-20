package com.woniuxy.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private  Integer aid;  //地址信息表
    private  String aname; //收件人名字
    private  String aphoe;  //手机号
    private  String address;   //地址详情
    private  Integer uid;   //用户ID
    private Integer defaults;//默认状态 1为默认地址

}
