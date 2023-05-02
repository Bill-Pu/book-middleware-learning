package com.learning.rabbitLisenter;

import com.learning.entity.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@RabbitListener(queues = "TestDirectQueue")
@Component
public class DirectConsumer {

    @RabbitHandler
    public void process(User map , Channel channel, Message message) throws IOException {
        System.out.println("消费者接受到的消息是："+map.toString());
        //由于配置设置了手动应答，所以这里要进行一个手动应答。注意：如果设置了自动应答，这里又进行手动应答，会出现double ack，那么程序会报错。
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
