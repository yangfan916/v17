package com.yangfan.v17cart.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yangfan.api.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Slf4j
@Component
public class SSOHandler {

    @Reference
    private ICartService cartService;

    @RabbitListener(queues = "sso-queue-cart")
    @RabbitHandler
    public void process(Map<String, String> map){
        log.info("队列监听到合并购物车的消息了");
        String nologinKey = map.get("nologinKey");
        String loginKey = map.get("loginKey");
        //合并购物车
        cartService.merge("user:cart:" + nologinKey, "user:cart:" + loginKey);
    }
}
