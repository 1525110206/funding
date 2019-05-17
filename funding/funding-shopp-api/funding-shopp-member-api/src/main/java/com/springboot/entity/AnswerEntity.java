package com.springboot.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerEntity extends BaseEntity {

    String context;
    Long qid;
    Long mid;
    Long hid;
    UserEntity userEntity;
    QuestionEntity questionEntity;


}
