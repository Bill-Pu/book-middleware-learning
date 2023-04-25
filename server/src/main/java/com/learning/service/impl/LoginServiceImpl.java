package com.learning.service.impl;

import com.learning.domain.LoginUser;
import com.learning.domain.ResponseResult;
import com.learning.entity.User;
import com.learning.service.LoginService;
import com.learning.utils.JwtUtil;
import com.learning.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author PYB
 * @Date 2023/4/25 20:02
 * @Version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;


    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (authenticate == null) {
            return ResponseResult.fail("用户名或密码错误");
        }
        LoginUser principal = (LoginUser) authenticate.getPrincipal();
        User user1 = principal.getUser();
        Long id = user1.getId();
        String jwt = JwtUtil.createJWT(String.valueOf(id));
        //authenticate存入redis
//        redisCache.setCacheObject("login:"+id.toString(),principal);
        redisCache.setRedisForString("login:"+id.toString(),principal);
        //把token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return new ResponseResult(200,"登陆成功",map);
    }
    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        redisCache.deleteObject("login:"+userid);
        return new ResponseResult(200,"退出成功");
    }
}
