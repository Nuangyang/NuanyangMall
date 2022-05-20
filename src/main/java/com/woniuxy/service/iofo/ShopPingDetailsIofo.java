package com.woniuxy.service.iofo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopPingDetailsIofo {

    private Integer sdid;//商品详情ID
    private String ganme;//商品名称
    private Integer gnum;//商品数量
    private double gprive;//商品价格
    private String iid;//商品图片
    private String address;//地址
    private long creationtime;//创建订单时间
    private int dinstart;//订单状态
    private Integer uid;//用户ID
    private long uuid;//订单编号

}
