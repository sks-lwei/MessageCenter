package com.lwei.message.instance;

import cn.hutool.extra.spring.SpringUtil;
import com.lwei.message.dto.Message;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * 简单消息
 *
 * @author sks.lwei
 * @date 2020/6/9 10:27
 */
@Log4j2
public class SimpleMessage implements MessageHandler{

    /**
     * 消息队列名称
     */
    private String queueName;

    /**
     * 交换器名称
     */
    private String exchangesName;

    /**
     * 通道
     */
    private Channel simpleChannel;

    /**
     * 开启通道
     */
    private void open(){
        Connection rabbitConnection = SpringUtil.getBean("RabbitMQConnection", Connection.class);
        try {
            simpleChannel = rabbitConnection.createChannel();
            // 声明（创建）队列
            simpleChannel.queueDeclare(queueName, false, false, false, null);
        } catch (IOException e) {
            log.error("通道/队列创建失败", e);
            simpleChannel = null;
        }
    }

    public SimpleMessage(String queueName) {
        this.queueName = queueName;
    }

    public SimpleMessage(String queueName, String exchangesName) {
        this.queueName = queueName;
        this.exchangesName = exchangesName;
    }

    @Override
    public Message send(Message message) {
        message.setQueueName(queueName);
        message.setExchangesName(exchangesName);

        open();
        try {
            //发送消息
            simpleChannel.basicPublish(exchangesName, queueName, null, message.getMsg());
            //标记消息发送成功
            message.setSuccess(true);
        } catch (IOException e) {
            log.error("消息发送失败", e);
        } finally {
            close();
        }
        return message;
    }

    @Override
    public Message receive(Message message, ReceiveCallback receiveCallback, CancelCallback cancelCallback) {

        open();
        try {
            // 监听队列
            simpleChannel.basicConsume(queueName, false, (consumerTag, delivery)->{
                long deliveryTag = delivery.getEnvelope().getDeliveryTag();
                message.setMsg(delivery.getBody());
                boolean result = receiveCallback.receive(message);
                if(result){
                    //消费成功
                    simpleChannel.basicAck(deliveryTag, false);
                    message.setSuccess(true);
                }else {
                    //消费失败
                    simpleChannel.basicNack(deliveryTag, false, true);
                }
            }, cancelCallback);

        } catch (IOException e) {
            log.error("监听失败", e);
        }
        return message;
    }

    /**
     * 关闭通道
     */
    public void close(){
        if(simpleChannel != null){
            try {
                simpleChannel.close();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
