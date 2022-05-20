package com.woniuxy.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopPing {

    private Integer sid; //购物车ID
    private Integer uid; //用户ID
    private Integer gid; //商品ID
    private Integer snum;//购物车商品个数

}
