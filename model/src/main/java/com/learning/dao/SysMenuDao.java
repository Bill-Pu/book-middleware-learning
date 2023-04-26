package com.learning.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learning.entity.Menu;

import java.util.List;

/**
 * 菜单表(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-26 21:01:59
 */
public interface SysMenuDao extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long id);

}

