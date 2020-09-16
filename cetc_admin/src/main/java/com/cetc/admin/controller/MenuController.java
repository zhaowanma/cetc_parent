package com.cetc.admin.controller;

import com.cetc.admin.service.SysMenuService;
import com.cetc.common.core.entity.Result;
import com.cetc.model.admin.SysMenu;
import com.cetc.model.log.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "资源控制层类")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping("/saveMenu")
    @LogAnnotation(module = "添加资源权限")
    @ApiOperation("资源保存接口")
    public Result saveMenu(@RequestBody SysMenu sysMenu){
       return sysMenuService.saveMenu(sysMenu);

    }

    @GetMapping("/findAllMenusVisble")
    @ApiOperation("查询所有资源接口")
    public Result findAllVisble(){
        return sysMenuService.findAllVisable();
    }


    @GetMapping("/findAllMenus")
    @ApiOperation("查询所有资源接口")
    public Result findAll(){
        return sysMenuService.findAll();
    }

    @GetMapping("/findTreeMenus")
    @ApiOperation("查询所有资源接口，返回树形结构数据")
    public Result findTreeMenus(){
        return sysMenuService.findTreeMenu();
    }

    @GetMapping("/findMenuById/{menuId}")
    @ApiOperation("根据资源id查询资源接口")
    public Result findMenuById(@PathVariable int menuId){
        return sysMenuService.findMenuById(menuId);
    }

    @DeleteMapping("/deleteMenuById/{menuId}")
    @LogAnnotation(module = "删除资源权限")
    @ApiOperation("根据资源id删除资源接口")
    public Result deleteMenuById(@PathVariable int menuId){
        return sysMenuService.deleteMenuById(menuId);
    }

    @PutMapping("/updateMenuById")
    @LogAnnotation(module = "修改资源权限")
    @ApiOperation("修改资源接口")
    public Result updateMenuById(@RequestBody SysMenu sysMenu){
        return sysMenuService.updateMenuById(sysMenu);
    }


}
