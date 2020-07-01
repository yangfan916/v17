package com.yangfan.publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
public class Producer {

    private static final String EXCHANGE_NAME = "fanout_exchange";

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
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        //4.发送消息到交换机上
        //参数1：交换器
        //参数2：队列名称
        for( int i = 0; i < 10; i++){
            String msg = "发送的第" + i + "条消息";
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        }


        System.out.println("发送消息成功");

    }
}
