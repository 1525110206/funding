package com.springboot.manager;

import com.springboot.entity.ManagerEntity;

import java.util.Map;

public interface ManagerServiceManager {

    public Map<String, Object> login(ManagerEntity managerEntity);


    public String md5PassSalt(String phone, String password);


    public Map<String, Object> getUser(String token);

}
