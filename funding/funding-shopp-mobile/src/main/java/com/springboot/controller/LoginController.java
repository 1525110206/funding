package com.springboot.controller;

import com.springboot.constants.BaseApiConstants;
import com.springboot.constants.TimeConstants;
import com.springboot.entity.ManagerEntity;
import com.springboot.entity.UserEntity;
import com.springboot.feign.ManagerFeign;
import com.springboot.feign.UserFeign;
import com.springboot.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController extends BaseController {

    private static final String LGOIN = "/login";
    private static final String MAIN = "main";
    private static final String MEMBER = "member";

    @Autowired
    private UserFeign userFeign;
    @Autowired
    private ManagerFeign managerFeign;

    //异步请求
    @PostMapping("/dologin")
    public String dologin(String phone, String password, String type, HttpSession session, HttpServletResponse response,
                          HttpServletRequest request) {
        if ("manager".equals(type)) {
            ManagerEntity managerEntity = new ManagerEntity();
            managerEntity.setPhone(phone);
            managerEntity.setPassword(password);

            Map<String, Object> login = managerFeign.login(managerEntity);
            Integer code = (Integer) login.get(BaseApiConstants.HTTP_CODE_NAME);
            if (!code.equals(BaseApiConstants.HTTP_200_CODE)) {
                String msg = (String) login.get("msg");
                return setError(request, msg, LGOIN);
            }

            // 登录成功之后,获取token.将这个token存放在cookie
            String token = (String) login.get("data");

            ManagerEntity manager = getManagerEntity(token);

            //把登录者的实体放在session域中
            session.setAttribute(TimeConstants.LOGIN_MANAGER, manager);

            System.out.println(manager.getId());

            CookieUtil.addCookie(response, TimeConstants.USER_TOKEN, token, TimeConstants.WEBUSER_COOKIE_TOKEN_TERMVALIDITY);
            return "redirect:/manager/index.html";


        } else if ("member".equals(type)) {
            UserEntity userEntity = new UserEntity();
            userEntity.setPassword(password);
            userEntity.setPhone(phone);

            Map<String, Object> login = userFeign.login(userEntity);

            Integer code = (Integer) login.get(BaseApiConstants.HTTP_CODE_NAME);
            if (!code.equals(BaseApiConstants.HTTP_200_CODE)) {
                String msg = (String) login.get("msg");
                return setError(request, msg, LGOIN);
            }

            // 登录成功之后,获取token.将这个token存放在cookie
            String token = (String) login.get("data");

            UserEntity member = getUserEntity(token);


            //把登录者的实体放在session域中
            session.setAttribute(TimeConstants.LOGIN_MEMBER, member);

            //System.out.println(session.getAttribute(TimeConstants.LOGIN_MEMBER));

            CookieUtil.addCookie(response, TimeConstants.USER_TOKEN, token, TimeConstants.WEBUSER_COOKIE_TOKEN_TERMVALIDITY);
            return "redirect:/member.html";
        }
        return LGOIN;
    }
}






