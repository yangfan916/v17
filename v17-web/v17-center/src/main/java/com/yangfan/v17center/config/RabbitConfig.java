package com.yangfan.v17center.config;

import com.yangfan.common.constant.MQConstant;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange initProductExchange(){
        TopicExchange productExchange = new TopicExchange(MQConstant.EXCHANGE.CENTER_PRODUCT_EXCHANGE, true, false);
        return productExchange;
    }
}
