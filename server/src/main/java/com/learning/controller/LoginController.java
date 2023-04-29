package com.learning.controller;

import com.learning.domain.ResponseResult;
import com.learning.entity.User;
import com.learning.service.LoginService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author PYB
 * @Date 2023/4/25 19:46
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class LoginController {
    @Resource
    private LoginService loginService;
    @RequestMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return loginService.login(user);
    }
    @RequestMapping("/login_out")
    public ResponseResult logOut(){
        return loginService.logout();
    }
    @RequestMapping("/register")
    public ResponseResult register(@RequestBody User user){
        return loginService.register(user);
    }
}
