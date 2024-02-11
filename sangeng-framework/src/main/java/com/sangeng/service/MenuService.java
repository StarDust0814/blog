package com.sangeng.service;

import com.sangeng.domain.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author RS.Meta
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2024-02-11 16:20:12
*/
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);
    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}
