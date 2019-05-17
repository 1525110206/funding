package com.springboot.api.service.impl;

import com.springboot.api.BaseApiService;
import com.springboot.api.UserService;
import com.springboot.entity.*;
import com.springboot.manager.AnswerServiceManager;
import com.springboot.manager.QuestionServiceManager;
import com.springboot.manager.TicketServiceManager;
import com.springboot.manager.UserServiceManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
public class UserServiceImpl extends BaseApiService implements UserService {

    @Autowired
    UserServiceManager userServiceManager;


    @Autowired
    TicketServiceManager ticketServiceManager;

    @Autowired
    QuestionServiceManager questionServiceManager;

    @Autowired
    AnswerServiceManager answerServiceManager;



    @Override
    public Map<String, Object> regist(@RequestBody UserEntity userEntity) {

//        if(StringUtils.isEmpty(userEntity.getUsername())){
//            return setResutParameterError("用户名称不能为空");
//        }
//
//        if(StringUtils.isEmpty(userEntity.getPassword())){
//            return setResutParameterError("密码不能为空");
//        }

        try {
            userServiceManager.regist(userEntity);
            return setResutSuccess();
        } catch (Exception e) {
            log.error("###regist() ERRPR:",e);
            return setResutError("注册失败!");
        }
    }

    @Override
    public Map<String, Object> login(@RequestBody UserEntity userEntity) {
        //向数据库进行查找数据
        //生成对应的token
        //key为自定义令牌，用户的userId作为value存放在redis
        //返回token
        return userServiceManager.login(userEntity);
    }

    @Override
    public Map<String, Object> getUser(@RequestParam(value="token") String token) {
        return userServiceManager.getUser(token);
    }


    @Override
    public Page queryPage(@RequestBody Map<String, Object> paramMap) {
        return userServiceManager.queryPage(paramMap);
    }

    @Override
    public int deleteByPrimaryKey(@RequestBody Map<String, Integer> paramMap) {
        return userServiceManager.deleteByPrimaryKey(paramMap);
    }

    @Override
    public boolean deleteBatch(@RequestBody Data data) {
        List<Integer> list = data.getDel_ids();
        int count = 0;
        for(Integer id : list){
            Map<String, Integer> paramMap = new HashMap<>();
            paramMap.put("id",id);
            userServiceManager.deleteByPrimaryKey(paramMap);
            count ++;
        }
        return count == list.size();
    }

    @Override
    public void updateAcctType(@RequestBody UserEntity userEntity) {
        userServiceManager.updateAcctType(userEntity);
    }

    @Override
    public void updateBasicinfo(@RequestBody UserEntity userEntity) {
        userServiceManager.updateBasicinfo(userEntity);
    }


    @Override
    public void updateEmail(@RequestBody UserEntity userEntity) {
        userServiceManager.updateEmail(userEntity);
    }

    @Override
    public Map<String, Object> sendauthcode(@RequestParam(value="authcode") String authcode, @RequestParam(value="email") String email) {
        if(StringUtils.isEmpty(authcode)){
            return setResutParameterError("验证码不能为空");
        }
        if(StringUtils.isEmpty(email)){
            return setResutParameterError("邮箱不能为空");
        }

        try {
            userServiceManager.sendauthcode(authcode, email);
            return setResutSuccess();
        } catch (Exception e) {
            log.error("###regist() ERRPR:",e);
            return setResutError("发送验证码失败!");
        }
    }

    @Override
    public Page queryQuestionList(@RequestBody Map<String, Object> paramMap) {
        return questionServiceManager.queryQuestionList(paramMap);
    }

    @Override
    public int queryPhoneCount(@RequestBody UserEntity userEntity) {
        return userServiceManager.queryPhoneCount(userEntity);
    }


    @Override
    public TicketEntity getTicketByMemberId(@RequestParam(value="id") Long id) {
        return ticketServiceManager.getTicketByMemberId(id);
    }

    @Override
    public void saveTicket(@RequestBody TicketEntity ticket) {
        ticketServiceManager.saveTicket(ticket);
    }

    @Override
    public void updatePstep(@RequestBody TicketEntity ticket) {
        ticketServiceManager.updatePstep(ticket);
    }

    @Override
    public void updatePiidAndPstep(@RequestBody TicketEntity ticket) {
        ticketServiceManager.updatePiidAndPstep(ticket);
    }


    @Override
    public void insertQuestion(@RequestBody QuestionEntity questionEntity) {
        questionServiceManager.insert(questionEntity);
    }

    @Override
    public Page queryAnswerPage(@RequestBody Map<String, Object> paramMap) {
        return answerServiceManager.queryAnswerList(paramMap);
    }

    @Override
    public void insertAnswer(@RequestBody AnswerEntity answerEntity) {
        answerServiceManager.insertAnswer(answerEntity);
    }

    @Override
    public UserEntity getMember(@RequestParam(value="search") Integer search) {
        return userServiceManager.getMember(search);
    }

    @Override
    public void updateEmailAndUsername(@RequestBody UserEntity userEntity) {
        userServiceManager.updateEmailAndUsername(userEntity);
    }

    @Override
    public UserEntity getMemberById(@RequestParam(value="memberid") Integer memberid) {
        return userServiceManager.getMember(memberid);
    }

    @Override
    public List<Map<String, Object>> queryCertByMemberid(@RequestParam(value="memberid") Integer memberid) {
        return userServiceManager.queryCertByMemberid(memberid);
    }

    @Override
    public Page getMemberByAuth(@RequestBody Map<String, Object> paramMap){
        return userServiceManager.getMemberByAuth(paramMap);
    }

    @Override
    public void updateAuthstatus(@RequestBody UserEntity userEntity) {
        userServiceManager.updateAuthstatus(userEntity);
    }

    @Override
    public void updateStatus(@RequestBody TicketEntity ticket) {
        ticketServiceManager.updateStatus(ticket);
    }

    @Override
    public Page queryMemberQuestionList(@RequestBody Map<String, Object> paramMap) {
        return questionServiceManager.queryMemberQuestionList(paramMap);
    }

    @Override
    public int deleteQuestionByPrimaryKey(@RequestBody Map<String, Integer> paramMap) {
        return questionServiceManager.deleteQuestionByPrimaryKey(paramMap);
    }

    @Override
    public int deleteAnswerByPrimaryKey(@RequestBody Map<String, Integer> paramMap) {
        return answerServiceManager.deleteAnswerByPrimaryKey(paramMap);
    }

    @Override
    public Page queryMemberAnswerList(@RequestBody Map<String, Object> paramMap) {
        return answerServiceManager.queryMemberAnswerList(paramMap);
    }

    @Override
    public Page queryManagerQuestionList(@RequestBody Map<String, Object> paramMap) {
        return questionServiceManager.queryManagerQuestionList(paramMap);
    }

    @Override
    public void updateAccountById(@RequestParam(value="id") Long id) {
        questionServiceManager.updateAccountById(id);
    }





   /* @Override
    public void insert(@RequestBody UserEntity userEntity) {
        userServiceManager.insert(userEntity);
    }*/




}
