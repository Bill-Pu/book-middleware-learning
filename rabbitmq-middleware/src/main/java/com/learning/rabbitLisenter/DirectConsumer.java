package com.learning.rabbitLisenter;

import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.entity.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@RabbitListener(queues = "LoginLogDirectQueue")
@Component
public class DirectConsumer {
    @Resource
    private ObjectMapper objectMapper;
    @RabbitHandler
    public void process(Map map , Channel channel, Message message) throws IOException {
//        User user = (User) map.get("data");
        System.out.println("消费者接受到的消息是："+map.toString());
//        LinkedHashMap data = (LinkedHashMap) map.get("data");
//        String s = objectMapper.writeValueAsString(data);
//
//        User user = BeanUtils.mapToBean(data, User.class);
        //由于配置设置了手动应答，所以这里要进行一个手动应答。注意：如果设置了自动应答，这里又进行手动应答，会出现double ack，那么程序会报错。
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
