package com.springboot.dao;

import com.springboot.entity.QuestionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface QuestionMapper {

    List<QuestionEntity> queryQuestionList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    public void insertQuestion(QuestionEntity questionEntity);


    List<QuestionEntity> queryMemberQuestionList(Map<String, Object> paramMap);


    Integer queryMemberQuestionCount(Map<String, Object> paramMap);


    List<QuestionEntity> queryManagerQuestionList(Map<String, Object> paramMap);


    int deleteQuestionByPrimaryKey(Integer id);

    void updateAccountById(Long id);

}
