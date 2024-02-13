package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Menu;
import com.sangeng.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult listAllMenu(@RequestParam(value = "status",required = false) String status,
                                      @RequestParam(value = "menuName",required = false) String menuName){
        return menuService.listAllMenu(status, menuName);
    }

    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){
         return menuService.addMenu(menu);
     }
}