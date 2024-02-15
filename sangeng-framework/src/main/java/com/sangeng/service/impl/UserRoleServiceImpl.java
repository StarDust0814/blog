package com.sangeng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.entity.UserRole;
import com.sangeng.service.UserRoleService;
import com.sangeng.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author RS.Meta
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2024-02-15 10:26:00
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




