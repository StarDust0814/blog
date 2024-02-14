package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Menu;
import com.sangeng.domain.vo.RoutersVo;
import com.sangeng.service.MenuService;
import com.sangeng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

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

     @GetMapping("/{id}")
    public ResponseResult getMenuInfo(@PathVariable Long id){
        return menuService.getMenuInfo(id);
     }

     @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
         return menuService.updateMenu(menu);
     }

     @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long menuId){
        return menuService.deleteMenu(menuId);
     }

    @GetMapping("/treeselect")
    public ResponseResult treeselect(){
        return menuService.selectAllMenu();
    }

}