package com.springboot.mq.producer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

//推送邮件信息
@Service("registerMailboxProducer")
public class RegisterMailboxProducer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;


    public void send(Destination destination, String json){

        jmsMessagingTemplate.convertAndSend(destination, json);
    }




}
