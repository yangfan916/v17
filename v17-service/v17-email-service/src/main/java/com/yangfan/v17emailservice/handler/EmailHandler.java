package com.yangfan.v17emailservice.handler;

import com.yangfan.api.IEmailService;
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
public class EmailHandler {

    @Autowired
    private IEmailService emailService;

    @RabbitListener(queues = "email-birthday-queue")
    @RabbitHandler
    public void processSendBirdy(Map<String, String> params){
        String to = params.get("to");
        String username = params.get("username");
        emailService.sendBirthdayMail(to, username);
    }
}
