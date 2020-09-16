package com.cetc.admin.controller;


import com.cetc.admin.service.SysUserService;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.common.core.utils.LoginUserUtil;
import com.cetc.model.admin.LoginSysUser;
import com.cetc.model.admin.SysMenu;
import com.cetc.model.admin.SysUser;

import com.cetc.model.log.LogAnnotation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@Api(tags = "用户控制层类")
public class UserController {
    @Autowired
    private SysUserService sysUserService;

   /*
    *根据用户名查询该用户是否存在
    *
   * */
    @GetMapping(value = "/users-anon/internal",params = "username")
    @ApiOperation("根据用户名查询用户接口，后端内部调用使用")
    public LoginSysUser findByUsername(String username) {
        return sysUserService.findByUsername(username);
    }

    @GetMapping(value = "/checkUsername/{username}")
    @ApiOperation("根据用户名查询用户接口，前端业务使用")
    public Result checkUsername(@PathVariable String username) {
        return sysUserService.checkUsername(username);
    }
    @PostMapping(value="/queryPageUsers")
    @ApiOperation("分页查询用户接口")
    public Result queryPageUsers(@RequestBody Map<String,Object> params){
        return sysUserService.queryPageUsers(params);
    }

    @GetMapping(value="/findRolesByUserId/{userId}")
    @ApiOperation("根据用户id查询用户接口")
    public Result findRolesByUserId(@PathVariable int userId){
        return sysUserService.findRolesByUserId(userId);
    }

    @PostMapping(value="/addUser")
    @LogAnnotation(module = "新增用户")
    @ApiOperation("新增用户的接口")
    public Result addUser(@RequestBody SysUser sysUser){
        return sysUserService.saveUpdatedUser(sysUser);
    }

    @PutMapping(value="/saveUpdatedUser")
    @LogAnnotation(module = "编辑用户")
    @ApiOperation("修改用户接口")
    public Result saveUpdatedUser(@RequestBody SysUser sysUser){
        return sysUserService.saveUpdatedUser(sysUser);
    }

    @PostMapping(value="/enabledUser")
    @LogAnnotation(module = "用户状态激活/停用")
    @ApiOperation("用户激活接口")
    public Result enabledUser(@RequestBody SysUser sysUser){
        return sysUserService.saveUpdatedUser(sysUser);
    }


    @PostMapping(value="/saveRoleByUserId/{userId}")
    @LogAnnotation(module = "分配用户角色")
    @ApiOperation("分配用户角色接口")
    public Result saveRoleByUserId(@PathVariable int userId, @RequestBody List<Integer> checkedRoles){
        return sysUserService.saveRoleByUserId(userId,checkedRoles);
    }


    @DeleteMapping(value = "/deleteByUserId/{userId}")
    @LogAnnotation(module = "删除用户")
    @ApiOperation("删除用户接口")
    public Result deleteByUserId(@PathVariable int userId){
        return sysUserService.deleteByUserId(userId);
    }

    /**
     * 该方法是提供给工作流提交申请是选择审批用户时用的
     * @return
     */
    @PostMapping("findUserByList")
    @ApiOperation("根据用户名组批量查询用户接口")
    public Result findUserByList(@RequestBody List<String> usernames){
        return sysUserService.findUserByUsernameList(usernames);
    }

    @GetMapping("checkPasswordExpireTime/{day}")
    @ApiOperation("用户密码过期查询接口")
   public Result checkPasswordExpireTime(@PathVariable int day){
        return sysUserService.checkPasswordExpireTime(day);
   }

   @GetMapping("checkPasswordConfim/{password}")
   @ApiOperation("用户密码校验接口")
   public Result checkPasswordConfim(@PathVariable String password){
        return sysUserService.checkPasswordConfim(password);
   }

    @PutMapping("updatePassword")
    @ApiOperation("用户密码修改接口")
    public Result updatePassword(@RequestBody Map map){
        return sysUserService.updatePassword(map);
    }

    @GetMapping("getByUsername/{username}")
    @ApiOperation("根据用户名查询用户")
    public Result getByUserName(@PathVariable String username){
        return sysUserService.getByUsername(username);
    }

    @GetMapping("findAll")
    public Result findAllUser(){
      return sysUserService.findAllUser();
    }

    @GetMapping("findUserListIsNotSys")
    @ApiOperation("查询非内置用户")
    public Result findUserListIsNotSys(){
        return sysUserService.findByType(false);
    }

    @GetMapping("unLocked/{username}")
    public Result unLocked(@PathVariable String username){
        return sysUserService.unlocked(username);
    }

}
