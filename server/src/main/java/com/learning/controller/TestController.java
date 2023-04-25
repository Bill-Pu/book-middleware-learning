package com.learning.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author PYB
 * @Date 2023/4/19 21:38
 * @Version 1.0
 */
@RestController
@Slf4j
public class TestController {
    @Resource
    private RedisTemplate redisTemplate;
    @RequestMapping("test")
    public void test(){
        redisTemplate.opsForValue().set("test","test");
        Object test = redisTemplate.opsForValue().get("test");
        System.out.println(test.toString());

    }
}
