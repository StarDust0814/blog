package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddRoleDto;
import com.sangeng.domain.dto.UpdateRoleDto;
import com.sangeng.domain.entity.Menu;
import com.sangeng.domain.vo.RoutersVo;
import com.sangeng.service.MenuService;
import com.sangeng.service.RoleService;
import com.sangeng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult listAllRole(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam(value = "roleName",required = false) String roleName,
                                      @RequestParam(value = "status",required = false)String status){
        return roleService.listAllRole(pageNum,pageSize,roleName,status);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody UpdateRoleDto updateRoleDto){
        return roleService.changeStatus(updateRoleDto);
     }



    @PostMapping
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto){
        return roleService.addRole(addRoleDto);
     }

     @GetMapping("/{id}")
    public ResponseResult getRoleInfo(@PathVariable Long id){
        return roleService.getRoleInfo(id);
     }
}
