package com.cetc.admin.service;


import com.cetc.common.core.entity.Result;
import com.cetc.model.admin.LoginSysUser;
import com.cetc.model.admin.SysUser;

import java.util.List;
import java.util.Map;

public interface SysUserService {

     Result unlocked(String username);

     LoginSysUser findByUsername(String username);

     Result checkUsername(String username);

     Result queryPageUsers(Map<String,Object> params);

     Result findRolesByUserId(int userId);

     Result saveUpdatedUser(SysUser sysUser);

     Result deleteByUserId(int userId);

     Result saveRoleByUserId(int userId, List<Integer> checkRoles);

//     Result queryAllUsers(Map<String,Object> params);

     Result findUserByUsernameList(List<String> usernames);

     Result checkPasswordExpireTime(int day);

     Result checkPasswordConfim(String password);

     Result updatePassword(Map map);

     Result getByUsername(String username);

     Result findAllUser();

     Result findByType(Boolean isSys);
}
