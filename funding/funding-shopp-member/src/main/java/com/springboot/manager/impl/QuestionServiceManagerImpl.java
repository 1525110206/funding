package com.springboot.manager.impl;

import com.springboot.dao.QuestionMapper;
import com.springboot.entity.Page;
import com.springboot.entity.QuestionEntity;
import com.springboot.manager.QuestionServiceManager;
import com.springboot.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceManagerImpl implements QuestionServiceManager {
    @Autowired
    QuestionMapper questionMapper;


    @Override
    public Page queryQuestionList(Map<String, Object> paramMap) {
        Page page = new Page();

        Integer pageno = (Integer) paramMap.get("pageno");
        Integer pageSize = (Integer) paramMap.get("pageSize");



        if(pageno <= 0){
            page.setPageno(1);
        }else{
            page.setPageno(pageno);
        }
        if(pageSize <= 0){
            page.setPageSize(10);
        }else{
            page.setPageSize(pageSize);
        }
        Integer startIndex = (pageno-1)*pageSize;
        paramMap.put("startIndex", startIndex);
        List<QuestionEntity> datas = questionMapper.queryQuestionList(paramMap);
        page.setDatas(datas);


        Integer count = questionMapper.queryCount(paramMap);
        Integer totalno = (count%pageSize) == 0? (count/pageSize):((count/pageSize)+1);
        page.setTotalSize(count);
        page.setTotalno(totalno);
        return page;
    }

    @Override
    public void insert(QuestionEntity questionEntity) {
        questionEntity.setCreated(DateUtils.getTimestamp());
        questionEntity.setUpdated(DateUtils.getTimestamp());
        questionMapper.insertQuestion(questionEntity);
    }

    @Override
    public Page queryMemberQuestionList(Map<String, Object> paramMap) {
        Page page = new Page();

        Integer pageno = (Integer) paramMap.get("pageno");
        Integer pageSize = (Integer) paramMap.get("pageSize");



        if(pageno <= 0){
            page.setPageno(1);
        }else{
            page.setPageno(pageno);
        }
        if(pageSize <= 0){
            page.setPageSize(10);
        }else{
            page.setPageSize(pageSize);
        }
        Integer startIndex = (pageno-1)*pageSize;
        paramMap.put("startIndex", startIndex);
        List<QuestionEntity> datas = questionMapper.queryMemberQuestionList(paramMap);
        page.setDatas(datas);


        Integer count = questionMapper.queryMemberQuestionCount(paramMap);
        Integer totalno = (count%pageSize) == 0? (count/pageSize):((count/pageSize)+1);
        page.setTotalSize(count);
        page.setTotalno(totalno);
        return page;
    }


    @Override
    public int deleteQuestionByPrimaryKey(Map<String, Integer> paramMap) {
        Integer id = paramMap.get("id");
        return questionMapper.deleteQuestionByPrimaryKey(id);
    }

    @Override
    public Page queryManagerQuestionList(Map<String, Object> paramMap) {
        Page page = new Page();

        Integer pageno = (Integer) paramMap.get("pageno");
        Integer pageSize = (Integer) paramMap.get("pageSize");



        if(pageno <= 0){
            page.setPageno(1);
        }else{
            page.setPageno(pageno);
        }
        if(pageSize <= 0){
            page.setPageSize(10);
        }else{
            page.setPageSize(pageSize);
        }
        Integer startIndex = (pageno-1)*pageSize;
        paramMap.put("startIndex", startIndex);
        List<QuestionEntity> datas = questionMapper.queryManagerQuestionList(paramMap);
        page.setDatas(datas);


        Integer count = questionMapper.queryCount(paramMap);
        Integer totalno = (count%pageSize) == 0? (count/pageSize):((count/pageSize)+1);
        page.setTotalSize(count);
        page.setTotalno(totalno);
        return page;
    }

    @Override
    public void updateAccountById(Long id) {
        questionMapper.updateAccountById(id);
    }


}
