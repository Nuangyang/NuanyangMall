package com.woniuxy.controller.form;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRessForm {

    private Integer sheng;//省ID
    private Integer shi;//市ID
    private String xiangqing;//详情信息

    private String sname;//收件人
    private String sphon;//收件人手机

}
