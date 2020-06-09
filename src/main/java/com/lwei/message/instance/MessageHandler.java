package com.lwei.message.instance;

import com.lwei.message.dto.Message;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.DeliverCallback;

/**
 * 消息处理器
 *
 * @author sks.lwei
 * @date 2020/6/8 17:32
 */
public interface MessageHandler {

    /**
     * 发送消息
     * @param message 消息体
     * @return 消息回执
     */
    Message send(Message message);

    /**
     * 接收消息
     * @param message 消息回执
     * @return 消息体
     */
    Message receive(Message message, ReceiveCallback receiveCallback, CancelCallback cancelCallback);

}
