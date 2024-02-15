package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddUserDto;
import com.sangeng.domain.dto.UpdateUserDto;
import com.sangeng.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author RS.Meta
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2024-01-23 19:00:17
*/
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult listAllUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult addUser(AddUserDto addUserDto);

    ResponseResult deleteUser(Long id);

    ResponseResult getUserInfo(Long id);

    ResponseResult updateUser(UpdateUserDto updateUserDto);
}
