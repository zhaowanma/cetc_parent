package com.cetc.activiti.service;

import com.cetc.common.core.entity.PageResult;
import com.cetc.common.core.entity.Result;

import java.util.List;
import java.util.Map;

public interface ProcessUserService  {

     Result createGroup(Map map);

     Result findGroups(PageResult pageResult);

     Result findGroupById(String id);

     Result updateGroupById(Map map);

     Result deleteGroupById(String id);

     Result createMemberShip(String groupId, List<String> usernameList);

     Result findGroupUsers(String groupId);

     Result findActUsers(Map map);

     Result findAllActUsers();

     Result addActUsers(List<String> usernameList);

     Result queryByName(Map map);

     Result findAllTransferActUsers();

}
