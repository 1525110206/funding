package com.springboot.dao;

import com.springboot.entity.AnswerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface AnswerMapper {

    List<AnswerEntity> queryAnswerList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    public void insertAnswer(AnswerEntity answerEntity);

    List<AnswerEntity> queryMemberAnswerList(Map<String, Object> paramMap);

    Integer queryMemberCount(Map<String, Object> paramMap);

    int deleteAnswerByPrimaryKey(Integer id);



}
