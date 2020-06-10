package com.lwei.message;

import com.lwei.message.dto.Message;
import com.lwei.message.instance.MessageHandler;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitMqApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testSend(){

        Message message = new Message();
        message.setMsg("heool".getBytes());

        MessageHandler simpleInstance = RabbitFactory.getSimpleInstance("test-simple-1");

        simpleInstance.send(message);
        System.out.println("消息发送完事");


        simpleInstance.receive(message, msg->{

            System.out.println("消费消息-》"+new String(msg.getMsg()));

            return true;

        }, null);


    }

}
