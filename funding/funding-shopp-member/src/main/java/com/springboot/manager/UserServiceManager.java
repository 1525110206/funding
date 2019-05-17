package com.springboot.manager;

import com.springboot.entity.Page;
import com.springboot.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface UserServiceManager {


    public void regist(UserEntity userEntity);


    public String md5PassSalt(String phone, String password);


    public void sendauthcode(String authcode, String email);

    public Map<String, Object> login(UserEntity userEntity);


    public Map<String, Object> getUser(String token);

    public Page queryPage(Map<String, Object> paramMap);

    public int deleteByPrimaryKey(Map<String, Integer> paramMap);

    public void updateAcctType(UserEntity userEntity);

    public void updateBasicinfo(UserEntity userEntity);

    public void updateEmail(UserEntity userEntity);

    public int queryPhoneCount(UserEntity userEntity);


    public UserEntity getMember(Integer id);

    public void insert(UserEntity userEntity);


    public void updateEmailAndUsername(UserEntity userEntity);

    UserEntity getMemberById(Integer memberid);

    List<Map<String, Object>> queryCertByMemberid(Integer memberid);

    public Page getMemberByAuth(Map<String, Object> paramMap);

    void updateAuthstatus(UserEntity userEntity);





}
