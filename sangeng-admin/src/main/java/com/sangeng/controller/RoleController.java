package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult listAllRole(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam(value = "roleName",required = false) String roleName,
                                      @RequestParam(value = "status",required = false)String status){
        return roleService.listAllRole(pageNum,pageSize,roleName,status);
    }
}
