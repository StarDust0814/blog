package com.sangeng.mapper;

import com.sangeng.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.vo.MenuTreeSelectVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author RS.Meta
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2024-02-11 16:20:12
* @Entity com.sangeng.domain.entity.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<MenuTreeSelectVo> selectAllMenu();
}


