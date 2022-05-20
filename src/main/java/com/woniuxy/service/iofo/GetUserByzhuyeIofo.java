package com.woniuxy.service.iofo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserByzhuyeIofo {

    private String uname;   //用户名
    private Integer state; //账户状态
    private String head;   //用户头像

}
