package com.woniuxy.controller.form;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data

@NoArgsConstructor
public class GetGoodsVagueForm {
//模糊查询
    private String gname;


    public GetGoodsVagueForm(String gname){
        //添加模糊查询条件
        String replace = gname.replace(" ", "");
        StringBuilder stringBuilder = new StringBuilder(replace);
        stringBuilder.insert(0, "%");
        stringBuilder.append('%');
        String string = stringBuilder.toString();
        this.gname=string;

    }
}
