package com.sangeng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.entity.User;
import com.sangeng.service.UserService;
import com.sangeng.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author RS.Meta
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2024-01-23 19:00:17
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




