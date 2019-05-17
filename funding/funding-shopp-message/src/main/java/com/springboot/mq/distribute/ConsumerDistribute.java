package com.springboot.mq.distribute;

import com.alibaba.fastjson.JSONObject;
import com.springboot.adapter.MessageAdapter;
import com.springboot.constants.MQInterfaceType;
import com.springboot.service.SMSAuthcodeService;
import com.springboot.service.SMSMailboxService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerDistribute {
    @Autowired
    private SMSMailboxService smsMailboxService;

    @Autowired
    private SMSAuthcodeService smsAuthcodeService;

    @JmsListener(destination = "mail_queue")
    public void distribute(String json){
        log.info("###消息服务##收到消息，消息内容 json：{}", json);

        if(StringUtils.isEmpty(json)){
            return;
        }

        JSONObject jsonObject = new JSONObject().parseObject(json);
        JSONObject header = jsonObject.getJSONObject("header");
        String interfaceType = header.getString("interfaceType");
        MessageAdapter messageAdapter = null;
        switch (interfaceType) {
            case MQInterfaceType.SMS_MAIL:
                messageAdapter = smsMailboxService;
                break;
            case MQInterfaceType.SMS_AUTH:
                messageAdapter = smsAuthcodeService;
                break;
            default:
                break;
        }
        JSONObject content = jsonObject.getJSONObject("content");
        messageAdapter.distribute(content);

    }



}
