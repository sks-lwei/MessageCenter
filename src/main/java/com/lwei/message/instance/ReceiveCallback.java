package com.lwei.message.instance;

import com.lwei.message.dto.Message;

/**
 * message
 *
 * @author sks.lwei
 * @date 2020/6/9 14:42
 */
@FunctionalInterface
public interface ReceiveCallback {


    boolean receive(Message message);

}
