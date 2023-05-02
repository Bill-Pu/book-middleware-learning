package com.learning.event;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;
import java.time.Clock;

/**
 * @Author PYB
 * @Date 2023/5/2 17:53
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class LoginEvent extends ApplicationEvent implements Serializable {
    private static final long serialVersionUID = 5209238116278216814L;
    private String userName;
    private String loginTime;
    private String ip;

    public LoginEvent(Object source, Clock clock, String userName, String loginTime, String ip) {
        super(source);
        this.userName = userName;
        this.loginTime = loginTime;
        this.ip = ip;
    }
    public LoginEvent(Object object){
        super(object);
    }
}
