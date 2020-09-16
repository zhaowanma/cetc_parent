package com.cetc.admin.dao;


import com.cetc.model.admin.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Mapper
@Repository
public interface RoleMenuDao {

    Set<SysMenu> findMenusByRoleIds(@Param("roleIds") Set<String> roleIds);

    void insertRoleMenuId(@Param("roleId") int roleId, @Param("menuId") int menuId);

    void deleteByRoleId(int roleId);

    void deleteByMenuId(int menuId);

    Set<Integer> findMenuIdsByRoleId(int roleId);
}
