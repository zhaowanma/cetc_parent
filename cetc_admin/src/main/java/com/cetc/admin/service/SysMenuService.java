package com.cetc.admin.service;


import com.cetc.common.core.entity.Result;
import com.cetc.model.admin.SysMenu;

import java.util.Set;

public interface SysMenuService {
    /*
    * 根据角色获取权限集合
    * */
    Set<SysMenu> findByRoleIds(Set<String> roleIds);

     Result saveMenu(SysMenu sysMenu);

     Result findAll();

    Result findAllVisable();

     Result findTreeMenu();

     Result findMenuById(int menuId);

     Result deleteMenuById(int menuId);

     Result updateMenuById(SysMenu sysMenu);
}
