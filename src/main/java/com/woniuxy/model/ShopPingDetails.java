package com.woniuxy.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopPingDetails {

    private Integer sdid;//商品详情ID
    private String ganme;//商品名称
    private Integer gnum;//商品数量
    private double gprive;//商品价格
    private String iid;//商品图片
    private String address;//地址
    private long creationtime;//创建订单时间
    private Integer dinstart;//订单状态
    private Integer uid; //用户ID
    private long uuid;//订单编号

}
