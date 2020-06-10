package com.lwei.message;

import com.lwei.message.instance.MessageHandler;
import com.lwei.message.instance.SimpleMessage;

/**
 * RabbitMQ工厂
 *
 * @author sks.lwei
 * @date 2020/6/8 17:29
 */
public class RabbitFactory {

    public static MessageHandler getSimpleInstance(String queueName){
        return new SimpleMessage(queueName);
    }
}
