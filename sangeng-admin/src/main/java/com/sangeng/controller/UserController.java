package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddUserDto;
import com.sangeng.domain.dto.UpdateUserDto;
import com.sangeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseResult addUser(@RequestBody AddUserDto addUserDto){
        return userService.addUser(addUserDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getUserInfo(@PathVariable Long id){
         return userService.getUserInfo(id);
     }

     @PutMapping
    public ResponseResult updateUser(@RequestBody UpdateUserDto updateUserDto){
        return userService.updateUser(updateUserDto);
     }
}
