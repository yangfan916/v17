package com.yangfan.v17search.config;

import com.yangfan.common.constant.MQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
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

    /**
     *  1.声明队列
     */
    @Bean
    public Queue initProductSearchQueue(){
        Queue queue = new Queue(MQConstant.QUEUE.CENTER_PRODUCT_EXCHANGE_SEARCH_QUEUE, true, false, false,null);
        return queue;
    }

    /**
     * 2.绑定交换机
     * @return
     */
    @Bean
    public TopicExchange initProductExchange(){
        TopicExchange productExchange = new TopicExchange(MQConstant.EXCHANGE.CENTER_PRODUCT_EXCHANGE, true, false);
        return productExchange;
    }

    @Bean
    public Binding bindProductSearchQueueToProductExchange(Queue initProductSearchQueue, TopicExchange initProductExchange){
        return BindingBuilder.bind(initProductSearchQueue).to(initProductExchange).with("product.add");
    }
}
