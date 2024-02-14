package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Menu;
import com.sangeng.domain.entity.RoleMenu;
import com.sangeng.domain.vo.*;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import com.sangeng.service.MenuService;
import com.sangeng.mapper.MenuMapper;
import com.sangeng.service.RoleMenuService;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author RS.Meta
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2024-02-11 16:20:12
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{
    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 如果是管理员
        if(id == 1){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;

        }
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        if(SecurityUtils.isAdmin()){
            menus = menuMapper.selectAllRouterMenu();
        }else{
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public ResponseResult listAllMenu(String status, String menuName) {
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(StringUtils.hasText(status),Menu::getStatus, status);
        menuLambdaQueryWrapper.like(StringUtils.hasText(menuName),Menu::getMenuName,menuName);
        List<Menu> list = list(menuLambdaQueryWrapper);
        List<MenuListVo> menus = list.stream().sorted(Comparator.comparing(Menu::getParentId).thenComparing(Menu::getOrderNum))
                .map(menu -> {
                    MenuListVo menuVo = BeanCopyUtils.copyBean(menu, MenuListVo.class);
                    return menuVo;
                })
                .collect(Collectors.toList());

        return ResponseResult.okResult(menus);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        return save(menu) ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult getMenuInfo(Long id) {
        Menu menu = getById(id);
        MenuInfoVo menuInfoVo = BeanCopyUtils.copyBean(menu, MenuInfoVo.class);
        return ResponseResult.okResult(menuInfoVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if(menu.getParentId().equals(menu.getId())){
            throw new SystemException(AppHttpCodeEnum.MENU_PARENT_ID_ERROR);
        }
        return updateById(menu) ?
                ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult deleteMenu(Long menuId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = menuMapper.selectAllRouterMenu();

        List<Menu> children = builderMenuTree(menus,menuId);

        if(!CollectionUtils.isEmpty(children)){
            throw new SystemException(AppHttpCodeEnum.EXIST_MENU_CHILDREN);
        }

        return removeById(menuId) ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult selectAllMenu() {
        List<MenuTreeSelectVo> menus = getBaseMapper().selectAllMenu();
        List<MenuTreeSelectVo> menusTree = builderMenuTreeSelect(menus,"0");
        return ResponseResult.okResult(menusTree);
    }

    @Override
    public ResponseResult getRoleMenuTreeSelect(Long id) {
        List<MenuTreeSelectVo> menus = getBaseMapper().selectAllMenu();
        List<MenuTreeSelectVo> menusTree = builderMenuTreeSelect(menus,"0");
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,id).select(RoleMenu::getMenuId);
        List<RoleMenu> list = roleMenuService.list(wrapper);
        RoleMenuTreeSelectVo roleMenuTreeSelectVo = new RoleMenuTreeSelectVo();

        List<String> checkedKeys = list.stream().map(RoleMenu::getMenuId).map(String::valueOf).collect(Collectors.toList());
        roleMenuTreeSelectVo.setCheckedKeys(checkedKeys);
        roleMenuTreeSelectVo.setMenus(menusTree);
        return ResponseResult.okResult(roleMenuTreeSelectVo);
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream().filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu,menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    private List<MenuTreeSelectVo> builderMenuTreeSelect(List<MenuTreeSelectVo> menus, String parentId) {
        List<MenuTreeSelectVo> menuTree = menus.stream().filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildrenFromTree(menu,menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream().filter(m->m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }

    private List<MenuTreeSelectVo> getChildrenFromTree(MenuTreeSelectVo menu, List<MenuTreeSelectVo> menus) {
        List<MenuTreeSelectVo> childrenList = menus.stream().filter(m->m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildrenFromTree(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}




