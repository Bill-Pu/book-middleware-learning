package com.learning.service;

import com.learning.domain.ResponseResult;
import com.learning.entity.User;

/**
 * @Author PYB
 * @Date 2023/4/25 20:00
 * @Version 1.0
 */
public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
