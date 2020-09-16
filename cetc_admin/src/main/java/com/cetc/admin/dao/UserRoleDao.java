package com.cetc.admin.dao;


import com.cetc.model.admin.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Mapper
@Repository
public interface UserRoleDao {

    Set<SysRole> findRolesByUserId(int userId);

    void deleteByUserId(int userId);

    void deleteByRoleId(int roleId);

    void saveUserIdAndRoleId(@Param("userId") int userId, @Param("roleId") int roleId);


}
