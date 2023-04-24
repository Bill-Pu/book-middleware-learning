package com.learning.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learning.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-24 18:27:18
 */
public interface UserDao extends BaseMapper<User> {

}

