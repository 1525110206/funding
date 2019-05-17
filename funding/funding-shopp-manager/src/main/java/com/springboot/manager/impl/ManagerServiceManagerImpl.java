package com.springboot.manager.impl;

import com.springboot.api.BaseApiService;
import com.springboot.constants.TimeConstants;
import com.springboot.dao.ManagerMapper;
import com.springboot.entity.ManagerEntity;
import com.springboot.manager.ManagerServiceManager;
import com.springboot.redis.BaseRedisService;
import com.springboot.token.TokenUtils;
import com.springboot.utils.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class ManagerServiceManagerImpl extends BaseApiService implements ManagerServiceManager {
    @Autowired
    ManagerMapper managerMapper;

    @Autowired
    private BaseRedisService baseRedisService;

    @Autowired
    private TokenUtils tokenUtils;

    public ManagerServiceManagerImpl() {
    }


    @Override
    public Map<String, Object> login(ManagerEntity managerEntity) {
        String phone = managerEntity.getPhone();
        String password = managerEntity.getPassword();
        String newPassword = md5PassSalt(phone, password);
        ManagerEntity managerPhoneAndPwd = managerMapper.getUserPhoneAndPwd(phone, newPassword);
        if(managerPhoneAndPwd == null){
            return setResutError("账户或者密码错误");
        }
        //生成对应的token
        String token = tokenUtils.getToken();
        Long userId = managerPhoneAndPwd.getId();
        baseRedisService.set(token, userId + "", TimeConstants.USER_TOKEN_TERMVALIDITY);

        return setResutSuccessData(token);
    }

    @Override
    public String md5PassSalt(String phone, String password) {
        String newPass = MD5Util.MD5(phone + password);
        return newPass;
    }

    @Override
    public Map<String, Object> getUser(String token) {
        //从redis中找到userid
        String userId = baseRedisService.get(token);

        if(StringUtils.isEmpty(userId)){
            return setResutError("用户已经过期");
        }
        Long newuserId1 = Long.parseLong(userId);
        ManagerEntity managerInfo = managerMapper.getUserInfo(newuserId1);
        managerInfo.setPassword(null);
        return setResutSuccessData(managerInfo);
    }
}
