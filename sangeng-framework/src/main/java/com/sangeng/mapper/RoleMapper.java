package com.sangeng.mapper;

import com.sangeng.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author RS.Meta
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2024-02-11 16:22:05
* @Entity com.sangeng.domain.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}




