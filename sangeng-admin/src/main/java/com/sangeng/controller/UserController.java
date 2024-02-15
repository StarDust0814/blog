package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult listAllUser(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam(value = "userName",required = false) String userName,
                                      @RequestParam(value = "phonenumber",required = false) String phonenumber,
                                      @RequestParam(value =  "status",required = false) String status){
        return userService.listAllUser(pageNum,pageSize,userName,phonenumber,status);
    }
}
