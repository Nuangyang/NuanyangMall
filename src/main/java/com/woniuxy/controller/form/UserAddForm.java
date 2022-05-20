package com.woniuxy.controller.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddForm {

    private String uname;
    private String upwd;
    private String email;

}
