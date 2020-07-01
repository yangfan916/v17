package com.yangfan.routing;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yangfan
 * @version 1.0
 * @description 消费者 去队列获取消息
 */
public class MyConsumer1 {

    private static final String EXCHANGE_NAME = "direct_exchange";
    private static final String QUEUE_NAME = "direct_exchange-queue-1";

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
        final Channel channel = connection.createChannel();


        //限流，最多只放行一个消息
        channel.basicQos(1);

        //声明队列 和 绑定交换机
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "football");

        //3.创建一个消费者对象，并且写好处理消息的逻辑
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               String message = new String(body);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("消费者1：接收到的消息是：" + message);

               //手工确认模式，告知MQ服务器，消息已经被正确处理
                //参数2：表示是否批量确认
                //true：则批量确认，假设nvelope.getDeliveryTag()=10，意味着将1-10的消息都确认
                //false：只确认10
               channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        //4.需要让消费者去监听队列
        //第二个参数：是否自动确认消息处理完了，false-需要手动确认（推荐用false）
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

}
