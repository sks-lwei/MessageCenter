package com.lwei.message.consumer;

import com.lwei.message.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 接收消息控制器
 *
 * @author sks.lwei
 * @date 2020/6/8 14:16
 */
public class ReceiveMessageHandler {

    private final static String QUEUE_NAME = "q_test_01";

    public static void main(String[] argv) throws Exception {

        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        //消息确认
//        channel.confirmSelect();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 定义队列的消费者
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");

        };

        // 监听队列
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});

    }

}
