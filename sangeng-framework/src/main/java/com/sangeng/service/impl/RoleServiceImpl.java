package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddRoleDto;
import com.sangeng.domain.dto.UpdateRoleDto;
import com.sangeng.domain.dto.UpdateRoleInfoDto;
import com.sangeng.domain.entity.Role;
import com.sangeng.domain.entity.RoleMenu;
import com.sangeng.domain.entity.Tag;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.RoleVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import com.sangeng.service.RoleMenuService;
import com.sangeng.service.RoleService;
import com.sangeng.mapper.RoleMapper;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
* @author RS.Meta
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2024-02-11 16:22:05
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        // 判断是否是管理员
        if(id == 1L){
            List<String> roleKeys = new ArrayList<String>();
            roleKeys.add("admin");
            return roleKeys;
        }
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult listAllRole(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleName),Role::getRoleName,roleName);
        queryWrapper.eq(StringUtils.hasText(status),Role::getStatus,status);
        Page<Role> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(UpdateRoleDto updateRoleDto) {
        String roleId = updateRoleDto.getRoleId();
        String status  = updateRoleDto.getStatus();
        if(!StringUtils.hasText(roleId) || !StringUtils.hasText(status)){
            throw new SystemException(AppHttpCodeEnum.FIELD_NOT_NULL);
        }

        Role role = getById(roleId);
        role.setStatus(status);

        return updateById(role) ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

    }

    @Override
    public ResponseResult treeselect() {
        return null;
    }

    @Override
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        Role role = new Role();

        role.setRoleName(addRoleDto.getRoleName());
        role.setRoleKey(addRoleDto.getRoleKey());
        role.setRoleSort(addRoleDto.getRoleSort());
        role.setStatus(addRoleDto.getStatus());

        role.setRemark(addRoleDto.getRemark());

        if(!save(role)){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        Long id = role.getId();
        List<RoleMenu> list = new ArrayList<>();
        for(String menuId : addRoleDto.getMenuIds()){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(id);
            roleMenu.setMenuId(Long.parseLong(menuId));
            list.add(roleMenu);
        }
        return roleMenuService.saveBatch(list)? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult getRoleInfo(Long id) {
        Role role = getById(id);
        RoleVo roleVo = new RoleVo();
        roleVo.setId(role.getId().toString());
        roleVo.setRemark(role.getRemark());
        roleVo.setRoleKey(role.getRoleKey());
        roleVo.setRoleName(role.getRoleName());
        roleVo.setRoleSort(role.getRoleSort().toString());
        roleVo.setStatus(role.getStatus());

        return ResponseResult.okResult(roleVo);
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        return removeById(id) ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult updateRoleInfo(UpdateRoleInfoDto updateRoleInfoDto) {
        Role role = new Role();
        role.setId(Long.parseLong(updateRoleInfoDto.getId()));
        role.setRoleName(updateRoleInfoDto.getRoleName());
        role.setRoleKey(updateRoleInfoDto.getRoleKey());
        role.setRoleSort(updateRoleInfoDto.getRoleSort());
        role.setStatus(updateRoleInfoDto.getStatus());
        role.setMenuIds(updateRoleInfoDto.getMenuIds());
        role.setRemark(updateRoleInfoDto.getRemark());

        return updateById(role) ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
}




