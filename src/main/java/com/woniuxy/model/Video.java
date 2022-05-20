package com.woniuxy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private Integer vid;
    private  String vname;
    private  String gid;
}
