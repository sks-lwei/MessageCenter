package com.lwei.message.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * message
 *
 * @author sks.lwei
 * @date 2020/6/8 17:33
 */
@Getter
@Setter
public class Message {

    private boolean success;

    /**
     * 消息队列名称
     */
    private String queueName;

    /**
     * 交换器名称
     */
    private String exchangesName;

    /**
     * 消息体
     */
    private byte[] msg;
}
