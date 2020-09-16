package com.cetc.admin.service.impl;

import com.cetc.admin.dao.UserDao;
import com.cetc.admin.dao.UserRoleDao;
import com.cetc.admin.sender.Sender;
import com.cetc.admin.service.SysMenuService;
import com.cetc.admin.service.SysUserService;
import com.cetc.admin.utils.RedisUtil;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.common.core.utils.LoginUserUtil;
import com.cetc.model.admin.LoginSysUser;
import com.cetc.model.admin.SysMenu;
import com.cetc.model.admin.SysRole;
import com.cetc.model.admin.SysUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl implements SysUserService {

   private static final Logger logger= LoggerFactory.getLogger(SysUserServiceImpl.class);


    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private Sender sender;

    @Autowired
    private RedisUtil redisUtil;



    @Override
    @Transactional
    public Result unlocked(String username) {
       try {
           if(redisUtil.hasHashKey("cetc_login_times",username)){
               redisUtil.hashDelete("cetc_login_times",username);
           }
           BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
           SysUser sysUser = userDao.findUserByUsername(username);
           sysUser.setPassword(encoder.encode(sysUser.getUsername()));
           userDao.updateUser(sysUser);
           return new Result(true,StatusCode.OK,"解锁成功");
       }catch (Exception e){
           e.printStackTrace();
           logger.error("******************用户解锁失败*****************"+e.getMessage());
           return new Result(false,StatusCode.ERROR,"解锁失败");
       }
    }

    @Override
    public  LoginSysUser findByUsername(String username) {
        SysUser sysUser = userDao.findUserByUsername(username);
        if(sysUser !=null){
            LoginSysUser loginAppUser = new LoginSysUser();
            BeanUtils.copyProperties(sysUser,loginAppUser);
            Set<SysRole> sysRoles = userRoleDao.findRolesByUserId(sysUser.getId());
            loginAppUser.setSysRoles(sysRoles);
            if(!CollectionUtils.isEmpty(sysRoles)){
                Set<String> roleIds = sysRoles.parallelStream().map(SysRole::getRoleId).collect(Collectors.toSet());
                if(!CollectionUtils.isEmpty(roleIds)){
                    Set<SysMenu> sysMenus = sysMenuService.findByRoleIds(roleIds);
                    if(!CollectionUtils.isEmpty(sysMenus)){
                        Set<String> permissions = sysMenus.parallelStream().map(SysMenu::getPermission).collect(Collectors.toSet());
                        loginAppUser.setPermissions(permissions);
                    }
                }

            }
            return loginAppUser;

        }
        return null;
    }

    @Override
    public Result checkUsername(String username) {
        SysUser user = userDao.findUserByUsername(username);
        if(user!=null){
            return new Result(true,StatusCode.OK,"OK",true);
        }
        return new Result(true,StatusCode.OK,"OK",false);
    }

    @Override
    public Result queryPageUsers(Map<String,Object> params) {
        try {
            PageHelper.startPage((int)params.get("pageNum"),(int)params.get("pageSize"));
            List<SysUser> users = userDao.queryAllUsers(params);
            for (SysUser sysUser: users) {
                sysUser.setStatus(true);
                int times=0;
                if(redisUtil.hasHashKey("cetc_login_times",sysUser.getUsername())){
                    times=(int)redisUtil.hashGet("cetc_login_times",sysUser.getUsername());
                    if(times<=0){
                        sysUser.setStatus(false);
                    }
                }
            }
            PageInfo<SysUser> sysUserPageInfo = new PageInfo<>(users);
            return new Result(true, StatusCode.OK, "查询用户成功", sysUserPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("******************检查用户是否锁定失败*****************"+e.getMessage());
            return new Result(false,StatusCode.ERROR,"error");
        }

    }

    @Override
    public Result findRolesByUserId(int userId) {
        Set<SysRole> roles = userRoleDao.findRolesByUserId(userId);
        return new Result(true,StatusCode.OK,"查询成功",roles);
    }

    @Override
    @Transactional
    public Result saveUpdatedUser(SysUser sysUser) {
        try{
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encode="";
            if(sysUser!=null&&sysUser.getId()>0){
                sysUser.setUpdateTime(new Date());
                SysUser user = userDao.findUserByUserId(sysUser.getId());
                if(user.getPassword().equals(sysUser.getPassword())){
                    sysUser.setPassword("");
                }else if(sysUser.getPassword()!=null&&!"".equals(sysUser.getPassword())){
                    encode = encoder.encode(sysUser.getPassword());
                }
                sysUser.setPassword(encode);
                userDao.updateUser(sysUser);

            }else{
                sysUser.setCreateTime(new Date());
                sysUser.setPasswordCheckTime(new Date());
                encode = encoder.encode(sysUser.getPassword());
                sysUser.setPassword(encode);
                sysUser.setUpdateTime(new Date());
                userDao.saveUser(sysUser);
            }
                sysUser.setDelflag(false);
                sender.send(sysUser);
            return new Result(true,StatusCode.OK,"Ok");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("修改、新增用户失败 reason: "+e.getMessage());
            throw new RuntimeException("修改、新增用户失败");
        }
    }

    @Override
    @Transactional
    public Result deleteByUserId(int userId) {
        try {
            SysUser user = userDao.findUserByUserId(userId);
            userDao.deleteByUserId(userId);
            userRoleDao.deleteByUserId(userId);
            user.setDelflag(true);
            sender.send(user);
            return new Result(true,StatusCode.OK,"删除成功");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("删除用户失败 reason: "+e.getMessage());
            throw new RuntimeException("删除用户失败");
        }

    }

    @Override
    @Transactional
    public Result saveRoleByUserId(int userId, List<Integer> checkRoles) {
        userRoleDao.deleteByUserId(userId);
        if(checkRoles!=null&&checkRoles.size()>0){
            for (int roleId:checkRoles) {
                userRoleDao.saveUserIdAndRoleId(userId,roleId);
            }
        }
        return new Result(true,StatusCode.OK,"OK");
    }

    @Override
    public Result findUserByUsernameList(List<String> usernames) {
        List<SysUser> sysUsers = new ArrayList<>();
        for (String username: usernames) {
            SysUser user = userDao.findUserByUsername(username);
            if(user!=null){
                sysUsers.add(user);
            }

        }
        return new Result(true,StatusCode.OK,"根据用户名查询所有的用户成功",sysUsers);
    }

    @Override
    public Result checkPasswordExpireTime(int day) {
        if(LoginUserUtil.getLoginSysUser().getType()){
            String username = LoginUserUtil.getLoginSysUser().getUsername();
            SysUser sysUser = userDao.findUserByUsername(username);
            Date passwordCheckTime = sysUser.getPasswordCheckTime();
            Calendar cal = Calendar.getInstance();
            cal.setTime(passwordCheckTime);
            cal.add(Calendar.DATE,day);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if(cal.getTime().before(new Date())){
                return new Result(true,StatusCode.OK,"已过期",true);
            }else if(encoder.matches(sysUser.getUsername(),sysUser.getPassword())) {
                return new Result(true,StatusCode.OK,"密码已经被重置",true);
            }
        }

        return new Result(true,StatusCode.OK,"未过期",false);
    }

    @Override
    public Result checkPasswordConfim(String password) {
        String oldPassword = LoginUserUtil.getLoginSysUser().getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return new Result(true,StatusCode.OK,"Ok",encoder.matches(password,oldPassword));
    }

    @Override
    @Transactional
    public Result updatePassword(Map map) {
        String password = (String)map.get("password");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        int id = LoginUserUtil.getLoginSysUser().getId();
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setPassword(encoder.encode(password));
        sysUser.setPasswordCheckTime(new Date());
        userDao.updateUser(sysUser);
        return new Result(true,StatusCode.OK,"修改密码成功");
    }

    @Override
    public Result getByUsername(String username) {
        SysUser user = userDao.findUserByUsername(username);
        return new Result(true,StatusCode.OK,"",user);
    }
    @Override
    public Result findAllUser() {
        try {
            List<SysUser> list = userDao.findAllUsers();
            return new Result(true,StatusCode.OK,"",list);
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"error");
        }
    }
    @Override
    public Result findByType(Boolean isSys) {
        List<SysUser> list = userDao.findByType(isSys);
        return new Result(true,StatusCode.OK,"",list);
    }


}
