package com.springboot.dao;

import com.springboot.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    public void saveUser(UserEntity userEntity);

    public UserEntity getUserPhoneAndPwd(@Param("phone") String userName, @Param("password") String password);

    public UserEntity getUserInfo(@Param("id") Long id);

    List<UserEntity> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    int deleteByPrimaryKey(Integer id);

    void updateAcctType(UserEntity userEntity);

    void updateBasicinfo(UserEntity userEntity);

    void updateEmail(UserEntity userEntity);

    Integer queryPhoneCount(UserEntity userEntity);


    void updateEmailAndUsername(UserEntity userEntity);

    UserEntity getMemberById(Integer memberid);

    List<Map<String, Object>> queryCertByMemberid(Integer memberid);


    List<UserEntity> getMemberByAuth(Map<String, Object> paramMap);

    Integer queryMemberByAuthCount(Map<String, Object> paramMap);


    void updateAuthstatus(UserEntity userEntity);

}
