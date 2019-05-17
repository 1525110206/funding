package com.springboot.constants;

public interface TimeConstants {

    //用户会话保存90天
    Long USER_TOKEN_TERMVALIDITY = 60 * 60 * 24 * 90l;
    int WEBUSER_COOKIE_TOKEN_TERMVALIDITY = 1000*60 * 60 * 24 * 90;
    String USER_TOKEN = "token";

    String LOGIN_MEMBER = "member";

    String LOGIN_MANAGER = "manager";


}
