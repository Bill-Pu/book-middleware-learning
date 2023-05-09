package com.learning.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author PYB
 * @Date 2023/5/3 16:31
 * @Version 1.0
 */
@Configuration
public class FanRabbitConfig {
    @Bean("FanoutQueueOne")
    public Queue fanoutQueue(){
        return new Queue("FanoutQueueOne",true);
    }
    @Bean("FanoutQueueTwo")
    public Queue fanoutQueueTwo(){
        return new Queue("FanoutQueueTwo",true);
    }
    @Bean("FanoutExchange")
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("FanoutExchange",true,false);
    }
    @Bean()
    public Binding binding(){
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }
    @Bean()
    public Binding bindingTwo(){
        return BindingBuilder.bind(fanoutQueueTwo()).to(fanoutExchange());
    }
}
