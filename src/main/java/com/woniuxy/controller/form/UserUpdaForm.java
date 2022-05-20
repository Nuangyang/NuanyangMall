package com.woniuxy.controller.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdaForm {

    private String pwd;
    private String shouji;
    private String sex;
    private String shengri;
}
