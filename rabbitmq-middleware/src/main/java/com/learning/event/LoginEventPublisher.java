package com.learning.event;

import javafx.util.converter.LocalDateStringConverter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Formatter;

/**
 * @Author PYB
 * @Date 2023/5/2 18:34
 * @Version 1.0
 */
@Component
public class LoginEventPublisher {
    @Resource
    private ApplicationEventPublisher publisher;
    public void publish(){
        LoginEvent userName = new LoginEvent(this, null, "userName", LocalDateTime.now().toString(), "00000");
        publisher.publishEvent(userName);
    }
}
