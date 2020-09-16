package com.cetc.authentication.controller;

import com.cetc.authentication.feign.UserClient;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.common.core.utils.LoginUserUtil;
import com.cetc.model.admin.LoginSysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping
@Api(tags = "鉴权控制层类")
public class OAuth2Controller {

    @Autowired
    private UserClient userClient;

    @GetMapping("/user-me")
    @ApiOperation("token校验接口")
    public Authentication principal(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @GetMapping("/getLoginUser")
    @ApiOperation("获取当前登录用户接口")
    public Result getLoginUser(){
        LoginSysUser loginSysUser = LoginUserUtil.getLoginSysUser();
        if(loginSysUser!=null){
              //任何用户至少有一个角色
            if(loginSysUser.getPermissions()!=null){
                loginSysUser.getPermissions().add("guest");
            }else{
                Set<String> permissions =new HashSet<>();
                permissions.add("guest");
                loginSysUser.setPermissions(permissions);
            }

               //内置一个开发者账号，这个账号要有root角色
            if("admin".equals(loginSysUser.getUsername())){
                loginSysUser.getPermissions().add("root");
            }
            return new Result(true,StatusCode.OK,"获取当前用户成功",loginSysUser);
        }

        return new Result(false,StatusCode.OVERTIME,"获取当前用户失败");
    }

   
}
