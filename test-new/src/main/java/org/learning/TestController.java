package org.learning;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

@RestController
@RequestMapping("test")
public class TestController {
    @Resource
    RedisTemplate redisTemplate;
    @GetMapping("/test1")
    //@WebLogAop(description = "说明")
    public String controllerTest() {
        // redisTemplate.opsForValue().set("delay", "1");
        // String delay = (String) redisTemplate.opsForValue().get("delay");
        // System.out.println(delay);
        PriorityQueue<Integer> objects = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 < o1 ?1:0;
            }
        });
        objects.offer(1);
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        return "test";
    }
}
