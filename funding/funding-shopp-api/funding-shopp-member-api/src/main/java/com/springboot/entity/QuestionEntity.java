package com.springboot.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionEntity extends BaseEntity{

    String title;
    String context;
    Long mid;
    Integer account;
    UserEntity userEntity;

}
