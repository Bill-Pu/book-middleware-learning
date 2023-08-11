package com.learning.controller;

import com.learning.aop.WebLogAop;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("test")
public class TestController {
    @Resource
    RedisTemplate redisTemplate;
    @GetMapping("/test1")
    @WebLogAop(description = "说明")
    public String controllerTest() {
        redisTemplate.opsForValue().set("delay", "1");
        String delay = (String) redisTemplate.opsForValue().get("delay");
        System.out.println(delay);
        return "test";
    }
}
