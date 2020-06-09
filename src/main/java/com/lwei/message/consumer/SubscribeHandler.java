package com.lwei.message.consumer;

import com.lwei.message.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 订阅消息控制器
 *
 * @author sks.lwei
 * @date 2020/6/8 16:16
 */
public class SubscribeHandler {

    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws Exception{

        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();

        //绑定exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        //一个新的自动删除的队列
        String queueName = channel.queueDeclare().getQueue();
        //绑定队列
        channel.queueBind(queueName, EXCHANGE_NAME, "");


        //监听消费消息
        channel.basicConsume(queueName, true, (consumerTag, message) -> {
            String msg = new String(message.getBody(), "UTF-8");
            System.out.println(consumerTag);
            System.out.println(" [1] Received '" + msg + "'");
        }, consumerTag -> {});

    }

}
