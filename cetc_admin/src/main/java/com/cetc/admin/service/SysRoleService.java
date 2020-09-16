package com.cetc.admin.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.admin.SysRole;

import java.util.List;
import java.util.Map;

public interface SysRoleService {

    Result findPageRoles(Map<String, Object> params);

    Result insert(SysRole sysRole);

    Result deleteByRoleId(int roleId);

    Result insertRoleMenus(int roleId,List<Integer> menuIds);

    Result findMenuIdsByRoleId(int roleId);

    Result findAllRoles();
}
