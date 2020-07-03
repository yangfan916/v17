package com.yangfan.v17smsservice.handler;

import com.yangfan.sms.api.ISMS;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Component
public class SMSHandler {

    @Autowired
    private ISMS sms;

    /**
     * 处理发送短信验证码
     */
    @RabbitListener(queues = "sms-code-queue")
    @RabbitHandler
    public void processSendCode(Map<String,String> map){
        String identification = map.get("identification");
        String code = map.get("code");
        sms.sendCodeMessage(identification, code);
    }
}
