package com.springboot.api;

import com.springboot.entity.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/member")
public interface UserService {

    @PostMapping("/regist")
    public Map<String, Object> regist(@RequestBody UserEntity userEntity);

    //登录成功后，生成对应的token，作为key，将用户userID作为value存放在redis中，返回token给客户端
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody UserEntity userEntity);


    //使用token查找用户信息
    @PostMapping("/getUser")
    public Map<String, Object> getUser(@RequestParam(value="token") String token);

    //分页获取用户
    @PostMapping("/queryPage")
    public Page queryPage(@RequestBody Map<String, Object> paramMap);

    @PostMapping("/deleteByPrimaryKey")
    public int deleteByPrimaryKey(@RequestBody Map<String, Integer> paramMap);

    //批量删除
    @PostMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody Data data);



    //实名认证，更新账户类型
    @PostMapping("/updateAcctType")
    public void updateAcctType(@RequestBody UserEntity userEntity);

    //实名认证，更新主要信息
    @PostMapping("/updateBasicinfo")
    public void updateBasicinfo(@RequestBody UserEntity userEntity);


    //实名认证，获取到达的步骤
    @PostMapping("/getTicketByMemberId")
    TicketEntity getTicketByMemberId(@RequestParam(value="id") Long id);


    @PostMapping("/saveTicket")
    void saveTicket(@RequestBody TicketEntity ticket);

    //实名认证，更新到达步骤
    @PostMapping("/updatePstep")
    void updatePstep(@RequestBody TicketEntity ticket);


    @PostMapping("/updatePiidAndPstep")
    void updatePiidAndPstep(@RequestBody TicketEntity ticket);

    @PostMapping("/updateEmail")
    void updateEmail(@RequestBody UserEntity userEntity);

    //实名认证，发送验证码
    @PostMapping("/sendauthcode")
    public Map<String, Object> sendauthcode(@RequestParam(value="authcode") String authcode, @RequestParam(value="email") String email);


    //分页获取问题
    @PostMapping("/queryQuestionPage")
    public Page queryQuestionList(@RequestBody Map<String, Object> paramMap);

    @PostMapping("/queryPhoneCount")
    public int queryPhoneCount(@RequestBody UserEntity userEntity);


    @PostMapping("/insert")
    public void insertQuestion(@RequestBody QuestionEntity questionEntity);



    //分页获取答案
    @PostMapping("/queryAnswerPage")
    public Page queryAnswerPage(@RequestBody Map<String, Object> paramMap);



    @PostMapping("/insertAnswer")
    public void insertAnswer(@RequestBody AnswerEntity answerEntity);


    //后台回显数据
    @PostMapping("/getMember")
    public UserEntity getMember(@RequestParam(value="search") Integer search);

    @PostMapping("/updateEmailAndUsername")
    void updateEmailAndUsername(@RequestBody UserEntity userEntity);

    /*@PostMapping("/insert")
    public void insert(@RequestBody UserEntity userEntity);*/

    @PostMapping("/getMemberById")
    UserEntity getMemberById(@RequestParam(value="memberid") Integer memberid);


    @PostMapping("/queryCertByMemberid")
    List<Map<String, Object>> queryCertByMemberid(@RequestParam(value="memberid") Integer memberid);


    @PostMapping("/getMemberByAuth")
    public Page getMemberByAuth(@RequestBody Map<String, Object> paramMap);


    @PostMapping("/updateAuthstatus")
    public void updateAuthstatus(@RequestBody UserEntity userEntity);

    @PostMapping("/updateStatus")
    public void updateStatus(@RequestBody TicketEntity ticket);

    @PostMapping("/queryMemberQuestionList")
    public Page queryMemberQuestionList(@RequestBody Map<String, Object> paramMap);


    @PostMapping("/deleteQuestionByPrimaryKey")
    public int deleteQuestionByPrimaryKey(@RequestBody Map<String, Integer> paramMap);

    @PostMapping("/deleteAnswerByPrimaryKey")
    public int deleteAnswerByPrimaryKey(@RequestBody Map<String, Integer> paramMap);


    @PostMapping("/queryMemberAnswerList")
    public Page queryMemberAnswerList(@RequestBody Map<String, Object> paramMap);

    @PostMapping("/queryManagerQuestionList")
    public Page queryManagerQuestionList(@RequestBody Map<String, Object> paramMap);

    @PostMapping("/updateAccountById")
    public void updateAccountById(@RequestParam(value="id") Long id);
}
