package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddUserDto;
import com.sangeng.domain.dto.UpdateUserDto;
import com.sangeng.domain.entity.Role;
import com.sangeng.domain.entity.User;
import com.sangeng.domain.entity.UserRole;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.UserInfoVo;
import com.sangeng.domain.vo.UserRoleInfoVo;
import com.sangeng.domain.vo.UserRoleVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import com.sangeng.service.RoleService;
import com.sangeng.service.UserRoleService;
import com.sangeng.service.UserService;
import com.sangeng.mapper.UserMapper;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author RS.Meta
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2024-01-23 19:00:17
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult userInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        // 非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }

        // 数据是否已存在判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        // 加密密码
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(userName), User::getUserName, userName);
        wrapper.eq(StringUtils.hasText(phonenumber),User::getPhonenumber,phonenumber);
        wrapper.eq(StringUtils.hasText(status), User::getStatus,status);

        Page<User> page = new Page<>(pageNum, pageSize);
        page(page,wrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addUser(AddUserDto addUserDto) {
        String userName = addUserDto.getUserName();
        String nickName = addUserDto.getNickName();
        String password = addUserDto.getPassword();
        String phonenumber = addUserDto.getPhonenumber();
        String email = addUserDto.getEmail();
        String sex = addUserDto.getSex();
        String status = addUserDto.getStatus();
        List<String> roleIds = addUserDto.getRoleIds();

        if(!StringUtils.hasText(userName)){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }



        // 数据是否已存在判断
        if(userNameExist(userName)){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(phoneExist(phonenumber)){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if(emailExist(email)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        // 加密密码
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUserName(userName);
        user.setNickName(nickName);
        user.setPassword(encodedPassword);
        user.setStatus(status);
        user.setEmail(email);
        user.setPhonenumber(phonenumber);
        user.setSex(sex);
        save(user);
        List<UserRole> userRoles = new ArrayList<>();
        for(String roleId : roleIds){
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(Long.parseLong(roleId));
            userRoles.add(userRole);
        }
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();


    }

    @Override
    public ResponseResult deleteUser(Long id) {
        Long userId = SecurityUtils.getUserId();
        if(userId.equals(id)){
            throw new SystemException(AppHttpCodeEnum.CURRENT_USER_DELETE_ERR);
        }
        return removeById(id) ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult getUserInfo(Long id) {
        User user = getById(id);
        UserRoleInfoVo userRoleInfoVo = BeanCopyUtils.copyBean(user, UserRoleInfoVo.class);
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id).select(UserRole::getRoleId);
        List<UserRole> list = userRoleService.list(wrapper);
        List<String> roleIds = list.stream().map(UserRole::getRoleId).map(String::valueOf).collect(Collectors.toList());
        UserRoleVo userRoleVo = new UserRoleVo();
        userRoleVo.setRoleIds(roleIds);
        userRoleVo.setUser(userRoleInfoVo);

        LambdaQueryWrapper<Role> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> roles = roleService.list(roleWrapper);
        userRoleVo.setRoles(roles);
        return ResponseResult.okResult(userRoleVo);
    }

    @Override
    public ResponseResult updateUser(UpdateUserDto updateUserDto) {
        String userName = updateUserDto.getUserName();
        String nickName = updateUserDto.getNickName();
        String email = updateUserDto.getEmail();
        String sex = updateUserDto.getSex();
        String status = updateUserDto.getStatus();
        List<String> roleIds = updateUserDto.getRoleIds();
        String id = updateUserDto.getId();

        User user = new User();
        user.setId(Long.parseLong(id));
        user.setUserName(userName);
        user.setNickName(nickName);
        user.setStatus(status);
        user.setEmail(email);
        user.setSex(sex);

        if(!updateById(user)){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,user.getId());
        if(!userRoleService.remove(wrapper)){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        List<UserRole> userRoles = roleIds.stream().map(rid -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(Long.parseLong(rid));
            return userRole;
        }).collect(Collectors.toList());


        if(!userRoleService.saveBatch(userRoles)){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return ResponseResult.okResult();
    }

    private boolean phoneExist(String phonenumber) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhonenumber,phonenumber);
        return count(wrapper) > 0;
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail,email);
        return count(wrapper) > 0;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getNickName, nickName);
        return count(userLambdaQueryWrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserName, userName);
        return count(userLambdaQueryWrapper) > 0;
    }
}




