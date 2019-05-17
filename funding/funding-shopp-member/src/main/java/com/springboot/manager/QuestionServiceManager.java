package com.springboot.manager;

import com.springboot.entity.Page;
import com.springboot.entity.QuestionEntity;

import java.util.Map;

public interface QuestionServiceManager {


    public Page queryQuestionList(Map<String, Object> paramMap);

    public void insert(QuestionEntity questionEntity);

    public Page queryMemberQuestionList(Map<String, Object> paramMap);

    public int deleteQuestionByPrimaryKey(Map<String, Integer> paramMap);

    public Page queryManagerQuestionList(Map<String, Object> paramMap);

    public void updateAccountById(Long id);
}
