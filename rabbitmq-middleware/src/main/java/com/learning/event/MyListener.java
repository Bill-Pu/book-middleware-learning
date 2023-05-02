package com.learning.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * @Author PYB
 * @Date 2023/5/2 18:28
 * @Version 1.0
 */
@Component
@EnableAsync
@Slf4j
public class MyListener implements ApplicationListener<LoginEvent> {
    @Override
    @Async
    public void onApplicationEvent(LoginEvent event) {
        log.info("Login event 事件触发: " + event);
    }
}
