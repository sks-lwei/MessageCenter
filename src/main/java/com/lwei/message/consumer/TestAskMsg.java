package com.lwei.message.consumer;

import com.lwei.message.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * message
 *
 * @author sks.lwei
 * @date 2020/6/8 16:35
 */
public class TestAskMsg {

    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws Exception{

        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        channel.queueBind("amq.gen-7Or-2lnX8Exq68PkHFqm5Q", EXCHANGE_NAME, "");

//        channel.basicAck();

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String message = new String(body, "UTF-8");
                System.out.println(message);


                System.out.println("确认消息");
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume("amq.gen-7Or-2lnX8Exq68PkHFqm5Q", consumer);

    }

}
