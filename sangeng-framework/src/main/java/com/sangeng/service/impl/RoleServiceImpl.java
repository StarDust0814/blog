package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Role;
import com.sangeng.domain.entity.Tag;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.service.RoleService;
import com.sangeng.mapper.RoleMapper;
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
}




