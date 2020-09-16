package com.cetc.admin.controller;

import com.cetc.admin.service.SysRoleService;
import com.cetc.common.core.entity.PageResult;
import com.cetc.common.core.entity.Result;
import com.cetc.model.admin.SysRole;
import com.cetc.model.log.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@Api(tags = "角色控制层类")
public class RoleController {

    @Autowired
    private SysRoleService sysRoleService;


    @PostMapping(value = "findPageRoles")
    @ApiOperation("分页查询角色接口")
    public Result findPageRoles(@RequestBody Map<String, Object> params){
        return sysRoleService.findPageRoles(params);
    }
    @GetMapping(value = "findAllRoles")
    @ApiOperation("查询所有角色接口")
   public Result findAllRoles(){
        return sysRoleService.findAllRoles();
   }


   @PostMapping(value = "saveRole")
   @LogAnnotation(module = "新增角色")
   @ApiOperation("保存/修改角色接口")
    public Result saveRole(@RequestBody SysRole sysRole){
        return sysRoleService.insert(sysRole);
    }

   @DeleteMapping(value = "/deleteRoleById/{roleId}")
   @LogAnnotation(module = "删除角色")
   @ApiOperation("删除角色接口")
   public Result deleteRoleById(@PathVariable int roleId){
        return sysRoleService.deleteByRoleId(roleId);
   }

   @PostMapping(value = "/insertRoleMenus/{roleId}")
   @LogAnnotation(module = "分配角色权限")
   @ApiOperation("保存角色权限接口")
   public Result insertRoleMenus(@PathVariable int roleId,@RequestBody List<Integer> menuIds){
       return sysRoleService.insertRoleMenus(roleId,menuIds);
   }
   @GetMapping(value = "/findMenuIdsByRoleId/{roleId}")
   @ApiOperation("查询角色权限接口")
   public Result findMenuIdsByRoleId(@PathVariable int roleId){
        return sysRoleService.findMenuIdsByRoleId(roleId);
   }

}
