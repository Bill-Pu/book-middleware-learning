package com.learning.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learning.dao.SysMenuDao;
import com.learning.entity.Menu;
import com.learning.service.user.SysMenuService;
import org.springframework.stereotype.Service;

/**
 * 菜单表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-04-26 21:02:01
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, Menu> implements SysMenuService {

}

