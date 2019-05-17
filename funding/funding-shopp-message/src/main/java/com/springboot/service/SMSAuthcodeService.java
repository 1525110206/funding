package com.springboot.service;

import com.alibaba.fastjson.JSONObject;
import com.springboot.adapter.MessageAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SMSAuthcodeService implements MessageAdapter {

    @Autowired
    private JavaMailSender mailSender; // 自动注入的Bean


    @Override
    public void distribute(JSONObject jsonObject) {
        String mail=jsonObject.getString("mail");
        String authcode=jsonObject.getString("authcode");
        log.info("###消费者收到消息... mail:{},userName:{}",mail,authcode);
        // 发送邮件
        // 开始发送邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail);
        message.setTo(mail); // 自己给自己发送邮件
        message.setSubject("验证码");
        message.setText("验证码："+authcode);
        mailSender.send(message);
    }
}
