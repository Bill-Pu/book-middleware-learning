package com.learning.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learning.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    void setUserRole(@Param("id") Long id, @Param("i") int i);
}