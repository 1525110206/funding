package com.springboot.manager;

import com.springboot.entity.AnswerEntity;
import com.springboot.entity.Page;

import java.util.Map;

public interface AnswerServiceManager {
    public Page queryAnswerList(Map<String, Object> paramMap);


    public int deleteAnswerByPrimaryKey(Map<String, Integer> paramMap);


    public Page queryMemberAnswerList(Map<String, Object> paramMap);


    public void insertAnswer(AnswerEntity answerEntity);

}
