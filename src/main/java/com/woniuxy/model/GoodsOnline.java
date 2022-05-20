package com.woniuxy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.text.Position;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsOnline {

    private Integer gid;//商品ID
    private String gname;//商品名称
    private Integer gnum;//商品数量
    private double gprive; //商品价格
    private  String gdescribe;//商品描述
    private Integer gcategory;//商品类别ID
    private String img; //商品图片路径
    private String position;//主页放置位置
    private String video;//主页视频

}
