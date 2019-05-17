
package com.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.constants.BaseApiConstants;
import com.springboot.entity.ManagerEntity;
import com.springboot.entity.UserEntity;
import com.springboot.feign.ManagerFeign;
import com.springboot.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;



@Controller
public class BaseController {
	@Autowired
	private UserFeign userFeign;

	@Autowired
	private ManagerFeign managerFeign;

	public UserEntity getUserEntity(String token) {
		Map<String, Object> userMap = userFeign.getUser(token);
		Integer code = (Integer) userMap.get(BaseApiConstants.HTTP_CODE_NAME);
		if (!code.equals(BaseApiConstants.HTTP_200_CODE)) {
			return null;
		}
		// 获取data参数
		LinkedHashMap linkedHashMap = (LinkedHashMap) userMap.get(BaseApiConstants.HTTP_DATA_NAME);
		String json = new JSONObject().toJSONString(linkedHashMap);
		UserEntity userEntity = new JSONObject().parseObject(json, UserEntity.class);
		return userEntity;

	}

	public ManagerEntity getManagerEntity(String token) {
		Map<String, Object> userMap = managerFeign.getUser(token);
		Integer code = (Integer) userMap.get(BaseApiConstants.HTTP_CODE_NAME);
		if (!code.equals(BaseApiConstants.HTTP_200_CODE)) {
			return null;
		}
		// 获取data参数
		LinkedHashMap linkedHashMap = (LinkedHashMap) userMap.get(BaseApiConstants.HTTP_DATA_NAME);
		String json = new JSONObject().toJSONString(linkedHashMap);
		ManagerEntity managerEntity = new JSONObject().parseObject(json, ManagerEntity.class);
		return managerEntity;

	}



	public String setError(HttpServletRequest request, String msg, String addres) {
		request.setAttribute("error", msg);
		return addres;
	}

}
