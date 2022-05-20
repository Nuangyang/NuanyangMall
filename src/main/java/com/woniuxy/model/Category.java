package com.woniuxy.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private Integer caid; //商品类别ID

    private String cname; //类别名

}
