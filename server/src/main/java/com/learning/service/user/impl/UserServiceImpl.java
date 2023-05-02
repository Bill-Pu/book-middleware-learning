package com.learning.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learning.dao.UserMapper;
import com.learning.entity.User;
import com.learning.service.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2023-04-24 18:27:21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public void putANewUser(User user) {
        userMapper.putANewUser(user);
    }
//    public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, Menu> implements SysMenuService {

}

