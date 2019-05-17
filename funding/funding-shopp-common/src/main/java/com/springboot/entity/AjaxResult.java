package com.springboot.entity;


import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class AjaxResult {

    private boolean success;

    private String message;

    private Page page;

    private Object data;

}
