package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddRoleDto;
import com.sangeng.domain.dto.UpdateRoleDto;
import com.sangeng.domain.dto.UpdateRoleInfoDto;
import com.sangeng.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author RS.Meta
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2024-02-11 16:22:05
*/
public interface RoleService extends IService<Role> {
    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult listAllRole(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(UpdateRoleDto updateRoleDto);

    ResponseResult treeselect();

    ResponseResult addRole(AddRoleDto addRoleDto);

    ResponseResult getRoleInfo(Long id);

    ResponseResult deleteRole(Long id);

    ResponseResult updateRoleInfo(UpdateRoleInfoDto updateRoleInfoDto);

    ResponseResult listAllRole();
}
