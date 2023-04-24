package com.learning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author PYB
 * @Date 2023/4/19 21:16
 * @Version 1.0
 */
@SpringBootApplication
@SpringBootConfiguration
@MapperScan(basePackages= "com.learning.dao")
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
