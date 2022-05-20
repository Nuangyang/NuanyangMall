package com.woniuxy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Integer oid; //订单ID
    private Integer uid; //用户ID
    private Integer gid; //商品ID
    private Integer sum; //商品数量


}
