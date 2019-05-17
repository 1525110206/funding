package com.springboot.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Page {

    private Integer pageSize; //一页的数目
    private Integer pageno;  //哪一页
    private List datas;
    private Integer totalSize; //总的数目
    private Integer totalno; //总的页数

    public Page() {
    }

    public Page(Integer pageno, Integer pageSize){
    }








}
