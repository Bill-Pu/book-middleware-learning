package com.learning.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.learning.dao.SysMenuDao;
import com.learning.dao.UserMapper;
import com.learning.domain.LoginUser;
import com.learning.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @Author PYB
 * @Date 2023/4/25 17:32
 * @Version 1.0
 */
@Service
public class UserDetailServerImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private SysMenuDao menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> eq = new LambdaQueryWrapper<User>().eq(User::getUserName, username);
        User user = userMapper.selectOne(eq);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<String> permissionKeyList =  menuMapper.selectPermsByUserId(user.getId());
//        //测试写法
//        List<String> list = new ArrayList<>(Arrays.asList("test"));
        return new LoginUser(user,permissionKeyList);
    }
}
