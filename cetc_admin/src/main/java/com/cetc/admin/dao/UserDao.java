package com.cetc.admin.dao;


import com.cetc.model.admin.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserDao {

    SysUser findUserByUsername(String username);

    List<SysUser> queryAllUsers(Map<String, Object> params);

    void updateUser(SysUser sysUser);

    void deleteByUserId(int userId);

    void saveUser(SysUser sysUser);

    SysUser findUserByUserId(int userId);

    List<SysUser> findAllUsers();

    List<SysUser> findByType(boolean isSys);
}
