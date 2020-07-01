package com.yangfan.work;

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

    private static final String QUEUE_NAME = "work-queue";

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

        //3.声明队列
        //如果队列不存在，就创建
        //第一个参数：消息队列的名称
        //第二个参数：消息是否持久化
        //第三个参数：该队列是否只为当前这个连接服务
        //第四个参数：队列是否自动删除（当不用的时候）
        channel.queueDeclare(QUEUE_NAME, false, false,false, null);

        //4.发送消息到队列上
        //参数1：交换器
        //参数2：队列名称
        for( int i = 0; i < 10; i++){
            String msg = "发送的第" + i + "条消息";
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }


        System.out.println("发送消息成功");

    }
}
