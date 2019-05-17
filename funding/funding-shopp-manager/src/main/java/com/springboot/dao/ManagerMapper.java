package com.springboot.dao;

import com.springboot.entity.ManagerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ManagerMapper {

    public ManagerEntity getUserPhoneAndPwd(@Param("phone") String userName, @Param("password") String password);


    public ManagerEntity getUserInfo(@Param("id") Long id);

}
