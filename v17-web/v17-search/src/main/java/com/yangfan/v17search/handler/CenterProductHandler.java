package com.yangfan.v17search.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yangfan.api.ISearchService;
import com.yangfan.common.constant.MQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
@Slf4j
@Component
@RabbitListener(queues = MQConstant.QUEUE.CENTER_PRODUCT_EXCHANGE_SEARCH_QUEUE)
public class CenterProductHandler {

    //1.声明队列 center-product-exchange-search-queue
    //2.绑定交换机
    //1，2步都是借助管理平台来实现的

    @Reference
    private ISearchService searchService;

    @RabbitHandler
    public void process(Long newId){
        log.info("处理了id为：{}的消息", newId);
        searchService.synById(newId);
    }


}
