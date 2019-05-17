package com.springboot.manager.impl;

import com.springboot.dao.AnswerMapper;
import com.springboot.entity.AnswerEntity;
import com.springboot.entity.Page;
import com.springboot.manager.AnswerServiceManager;
import com.springboot.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class AnswerServiceManagerImpl implements AnswerServiceManager {

    @Autowired
    AnswerMapper answerMapper;


    @Override
    public Page queryAnswerList(Map<String, Object> paramMap) {
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
        List<AnswerEntity> datas = answerMapper.queryAnswerList(paramMap);
        page.setDatas(datas);


        Integer count = answerMapper.queryCount(paramMap);
        Integer totalno = (count%pageSize) == 0? (count/pageSize):((count/pageSize)+1);
        page.setTotalSize(count);
        page.setTotalno(totalno);
        return page;
    }

    @Override
    public int deleteAnswerByPrimaryKey(Map<String, Integer> paramMap) {
        Integer id = paramMap.get("id");
        return answerMapper.deleteAnswerByPrimaryKey(id);
    }

    @Override
    public Page queryMemberAnswerList(Map<String, Object> paramMap) {
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
        List<AnswerEntity> datas = answerMapper.queryMemberAnswerList(paramMap);
        page.setDatas(datas);


        Integer count = answerMapper.queryMemberCount(paramMap);
        Integer totalno = (count%pageSize) == 0? (count/pageSize):((count/pageSize)+1);
        page.setTotalSize(count);
        page.setTotalno(totalno);
        return page;
    }

    @Override
    public void insertAnswer(AnswerEntity answerEntity) {
        answerEntity.setCreated(DateUtils.getTimestamp());
        answerEntity.setUpdated(DateUtils.getTimestamp());
        answerMapper.insertAnswer(answerEntity);
    }
}
