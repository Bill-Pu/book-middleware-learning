package com.learning.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author PYB
 * @Date 2023/4/19 21:38
 * @Version 1.0
 */
@RestController
@Slf4j
public class TestController {
    @RequestMapping("test")
    public void test(){
        log.info("test");
        log.debug("test");
        log.error("test");
    }
}
