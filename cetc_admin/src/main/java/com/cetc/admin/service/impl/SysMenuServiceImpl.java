package com.cetc.admin.service.impl;


import com.cetc.admin.dao.MenuDao;
import com.cetc.admin.dao.RoleMenuDao;
import com.cetc.admin.entity.TreeMenu;
import com.cetc.admin.service.SysMenuService;
import com.cetc.admin.utils.TreeUtil;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.common.core.utils.LoginUserUtil;
import com.cetc.model.admin.LoginSysUser;
import com.cetc.model.admin.SysMenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Autowired
    private MenuDao menuDao;



    @Override
    public Set<SysMenu> findByRoleIds(Set<String> roleIds) {

       return roleMenuDao.findMenusByRoleIds(roleIds);

    }
    @Override
    public Result saveMenu(SysMenu sysMenu){
        sysMenu.setCreateTime(new Date());
        sysMenu.setUpdateTime(new Date());
       menuDao.insertMenu(sysMenu);
       return new Result(true, StatusCode.OK,"新增权限资源成功");
    }

    @Override
    public Result findAll() {
        List<SysMenu> menus = menuDao.findAll();
        return new Result(true,StatusCode.OK,"查询成功",menus);
    }

    @Override
    public Result findAllVisable() {
        List<SysMenu> menus = menuDao.findAllVisable();
        return new Result(true,StatusCode.OK,"查询成功",menus);
    }


    @Override
    public Result findTreeMenu() {
        List<TreeMenu> treeMenu = menuDao.findTreeMenu();
        List<TreeMenu> treeList = TreeUtil.getTreeList(-1, treeMenu);
      return new Result(true,StatusCode.OK,"OK",treeList);
    }

    @Override
    public Result findMenuById(int menuId) {
        SysMenu menu = menuDao.findMenuById(menuId);
        return new Result(true,StatusCode.OK,"  OK",menu);
    }

    @Override
    @Transactional
    public Result deleteMenuById(int menuId) {
        List<SysMenu> menusByParentId = menuDao.findMenusByParentId(menuId);
        for (SysMenu sysMenu: menusByParentId) {
            deleteMenuById(sysMenu.getId());
        }
        roleMenuDao.deleteByMenuId(menuId);
        menuDao.deleteMenuById(menuId);

        return new Result(true,StatusCode.OK,"删除成功");
    }

    @Override
    public Result updateMenuById(SysMenu sysMenu) {
        menuDao.updateMenuById(sysMenu);
        return new Result(true,StatusCode.OK,"修改成功");
    }


}
