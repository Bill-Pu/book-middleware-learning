package com.learning.controller;

import com.learning.entity.User;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class TestController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @GetMapping("/sendMessage")
    public String sendDirectMessage(){
        String messageId = UUID.randomUUID().toString();
        String messageData = "test message,hello!";
        String current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Map<String,Object> map = new HashMap<>();
        map.put("messageId",messageId);
        map.put("data",messageData);
        map.put("current",current);
        User user = new User();
        user.setUserName("测试发送对象");
        rabbitTemplate.convertAndSend("TestDirectExchange", "123", user, new CorrelationData(UUID.randomUUID().toString()));
        return "ok";
    }
}
