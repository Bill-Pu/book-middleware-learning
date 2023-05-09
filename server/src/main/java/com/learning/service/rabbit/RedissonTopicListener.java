package com.learning.service.rabbit;

import com.learning.entity.User;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author PYB
 * @Date 2023/5/4 20:42
 * @Version 1.0
 */
@Component
public class RedissonTopicListener implements ApplicationRunner, Ordered {

    @Resource
    private RedissonClient redissonClient;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        RTopic userLogin = redissonClient.getTopic("userLogin");
        userLogin.addListener(User.class, (charSequence, p) -> {
            System.out.println(p);
        });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
