package com.cetc.admin.dao;

import com.cetc.model.admin.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleDao {

    List<SysRole> findAll();

    void insert(SysRole sysRole);

    void updateRoleById(SysRole sysRole);

    void deleteByRoleId(int roleId);


}
