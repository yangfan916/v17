package com.yangfan.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yangfan
 * @version 1.0
 * @description topic模式-规则匹配 *-->匹配一个单词 #-->匹配零个获取多个单词
 */
public class Producer {

    private static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {

        //1. 创建连接，连接上RabbitMQ服务器
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.149.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("yf_mq");
        connectionFactory.setUsername("yangfan");
        connectionFactory.setPassword("123456");

        Connection connection = connectionFactory.newConnection();

        //2. 基于这个连接的对象，创建对应的通道
        Channel channel = connection.createChannel();

        //3.声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        //4.发送消息到交换机上
        //参数1：交换器
        //参数2：队列名称

        String msg = "足球消息：今晚国足只要打平就能出线";
        channel.basicPublish(EXCHANGE_NAME, "football.guozu", null, msg.getBytes());

        String msg2 = "篮球CBA：周琦发边线球真的出神入化";
        channel.basicPublish(EXCHANGE_NAME, "basketball.CBA", null, msg2.getBytes());

        String msg3 = "篮球NBA：詹姆斯带队欲取10连胜";
        channel.basicPublish(EXCHANGE_NAME, "basketball.NBA", null, msg3.getBytes());


        System.out.println("发送消息成功");

    }
}
