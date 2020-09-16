package com.cetc.admin.service.impl;

import com.cetc.admin.dao.RoleDao;
import com.cetc.admin.dao.RoleMenuDao;
import com.cetc.admin.dao.UserRoleDao;
import com.cetc.admin.service.SysRoleService;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.admin.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Override
    public Result findPageRoles(Map<String, Object> params) {
        PageHelper.startPage((int)params.get("pageNum"),(int)params.get("pageSize"));
        List<SysRole> roles = roleDao.findAll();
        PageInfo<SysRole> sysRolePageInfo = new PageInfo<>(roles);
        Result result = new Result(true, StatusCode.OK,"查询角色成功",sysRolePageInfo);
        return result;
    }

    @Override
    public Result insert(SysRole sysRole) {
        if(sysRole.getRoleId()!=null){
            sysRole.setUpdateTime(new Date());
            roleDao.updateRoleById(sysRole);
        }else{
            sysRole.setCreateTime(new Date());
            sysRole.setUpdateTime(new Date());
            roleDao.insert(sysRole);
        }
        return new Result(true,StatusCode.OK,"OK");
    }
    @Override
    @Transactional
    public Result deleteByRoleId(int roleId){
        roleDao.deleteByRoleId(roleId);
        roleMenuDao.deleteByRoleId(roleId);
        userRoleDao.deleteByRoleId(roleId);
        return new Result(true,StatusCode.OK,"删除角色成功");
    }

    @Override
    @Transactional
    public Result insertRoleMenus(int roleId, List<Integer> menuIds) {
        roleMenuDao.deleteByRoleId(roleId);
        if(menuIds!=null&&menuIds.size()>0){
            for (int menuId:menuIds) {
                roleMenuDao.insertRoleMenuId(roleId,menuId);
            }
        }
        return new Result(true,StatusCode.OK,"成功");
    }
    @Override
    public Result findMenuIdsByRoleId(int roleId){
        Set<Integer> menuIdsByRoleId = roleMenuDao.findMenuIdsByRoleId(roleId);
        return new Result(true,StatusCode.OK,"查询成功",menuIdsByRoleId);
    }

    @Override
    public Result findAllRoles() {
        List<SysRole> roles = roleDao.findAll();
        return new Result(true,StatusCode.OK,"OK",roles);
    }

}
