package com.springboot.controller;

import com.springboot.entity.AjaxResult;
import com.springboot.entity.UserEntity;
import com.springboot.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class RegistController {


    @Autowired
    private UserFeign userFeign;

    @RequestMapping("/regist")
    @ResponseBody
    public Object regist(UserEntity userEntity, HttpServletRequest request) {

        AjaxResult result = new AjaxResult();
        try {
            Integer membercount = userFeign.queryPhoneCount(userEntity);
            if(membercount != 0){
                result.setSuccess(false);
                result.setMessage("该手机号已被注册");
            }else{
                Map<String, Object> registMap = userFeign.regist(userEntity);
                result.setSuccess(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }


}
