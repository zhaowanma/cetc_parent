package com.cetc.admin.dao;

import com.cetc.admin.entity.TreeMenu;
import com.cetc.model.admin.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuDao {

    void insertMenu(SysMenu sysMenu);

    List<SysMenu> findAll();

    List<SysMenu> findAllVisable();

    List<TreeMenu> findTreeMenu();

    SysMenu findMenuById(int menuId);

    void deleteMenuById(int menuId);

    void updateMenuById(SysMenu sysMenu);

    List<SysMenu> findMenusByParentId(int parentId);
}
