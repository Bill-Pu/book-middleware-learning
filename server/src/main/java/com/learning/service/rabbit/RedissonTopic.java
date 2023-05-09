package com.learning.service.rabbit;

import com.learning.entity.User;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author PYB
 * @Date 2023/5/4 20:37
 * @Version 1.0
 */
@Service
public class RedissonTopic {
    @Resource
    private RedissonClient redissonClient;
    public void publishMessage(User user){
        RTopic userLogin = redissonClient.getTopic("userLogin");
        userLogin.publish(user);
    }
}
