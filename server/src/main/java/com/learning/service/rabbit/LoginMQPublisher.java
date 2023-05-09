package com.learning.service.rabbit;

import com.learning.domain.LoginUser;
import com.learning.entity.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author PYB
 * @Date 2023/5/3 19:12
 * @Version 1.0
 */
@Service
public class LoginMQPublisher {
    @Resource
    private RabbitTemplate rabbitTemplate;
    public void publishLoginMessage(User user1){
        String messageId = UUID.randomUUID().toString();
        String current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Map<String,Object> map = new HashMap<>();
        map.put("messageId",messageId);
        map.put("data",user1);
        map.put("current",current);
        rabbitTemplate.convertAndSend("TestDirectExchange", "user.login", map);
    }
}
