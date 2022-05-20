package com.woniuxy.controller.form;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DelShoppingForm {
    //要删除的购物车ID
    private Integer gid;
}
