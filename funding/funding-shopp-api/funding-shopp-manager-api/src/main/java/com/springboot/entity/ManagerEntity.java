package com.springboot.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerEntity extends BaseEntity {

    private String userName;
    private String password;
    private String phone;
    private String email;
}
