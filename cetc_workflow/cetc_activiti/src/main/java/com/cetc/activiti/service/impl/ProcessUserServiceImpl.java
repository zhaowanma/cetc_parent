package com.cetc.activiti.service.impl;

import com.cetc.activiti.feign.UserClient;
import com.cetc.activiti.service.ProcessUserService;
import com.cetc.common.core.entity.PageResult;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.admin.LoginSysUser;
import com.cetc.model.admin.SysUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessUserServiceImpl implements ProcessUserService {

   private static final Logger logger = LoggerFactory.getLogger(ProcessUserService.class);

    @Autowired
    private IdentityService identityService;

    @Autowired
    private UserClient userClient;

    @Override
    public Result createGroup(Map map) {
        try {
            String id=(String)map.get("id");
            String name=(String)map.get("name");
            String type =(String)map.get("type");
            Group group = identityService.newGroup(id);
            group.setName(name);
            group.setType(type);
            identityService.saveGroup(group);
            return new Result(true, StatusCode.OK,"用户组创建成功");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("**********用户组创建失败reason: "+e.getMessage()+"***********");
            return new Result(false,StatusCode.ERROR,"用户组创建失败");
        }

    }

    @Override
    public Result findGroups(PageResult pageResult) {
        try {
            int pageStart = (pageResult.getPageNum()-1)*pageResult.getPageSize();
            int pageSize=pageResult.getPageSize();

            // int pageEnd = pageResult.getPageSize()*pageResult.getPageNum();
            GroupQuery groupQuery = identityService.createGroupQuery().orderByGroupId().desc();
            PageInfo info = new PageInfo();
            info.setTotal(groupQuery.list().size());
            info.setList(groupQuery.listPage(pageStart,pageSize));
           return new Result(true, StatusCode.OK,"查询成功",info);
        }catch (Exception e){
            logger.error("**********查询所有用户组失败reason: "+e.getMessage()+"***********");
            e.printStackTrace();
           return new Result(false, StatusCode.ERROR,"查询失败");
        }
    }

    @Override
    public Result findGroupById(String id) {
        try {
            GroupQuery groupQuery = identityService.createGroupQuery().groupId(id);
            Group group = groupQuery.singleResult();
            return new Result(true,StatusCode.OK,"查询成功",group);
        }catch (Exception e){
            logger.error("**********根据id查询用户组失败reason: "+e.getMessage()+"***********");
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"查询失败");
        }
    }

    @Override
    public Result updateGroupById(Map map) {
        try {
            String id=(String)map.get("id");
            String name=(String)map.get("name");
            String type =(String)map.get("type");
            Group group = identityService.createGroupQuery().groupId(id).singleResult();
            if(!group.getName().equals(name)||!group.getType().equals(type)){
                group.setName(name);
                group.setType(type);
                identityService.saveGroup(group);
            }
            return new Result(true,StatusCode.OK,"修改成功");
        }catch (Exception e){
            logger.error("**********根据id修改用户组失败reason: "+e.getMessage()+"***********");
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"修改失败");
        }

    }

    @Override
    public Result deleteGroupById(String id) {
        try {
            identityService.deleteGroup(id);
            return new Result(true,StatusCode.OK,"删除用户组成功");
        }catch (Exception e){
            logger.error("**********根据id删除用户组失败reason: "+e.getMessage()+"***********");
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"删除用户组失败");
        }

    }

    /**
     *  该方法创建工作流用户和用户组的关系
     * @param groupId
     * @param usernameList
     * @return
     */
    @Override
    public Result createMemberShip(String groupId, List<String> usernameList) {
        try {
            List<User> list = identityService.createUserQuery().memberOfGroup(groupId).list();
            List<String> groupUserIds = new ArrayList<>();

            if(list!=null&&list.size()>0){
                for (User groupUser: list) {
                    groupUserIds.add(groupUser.getId());
                }
                for (User user: list) {
                   if(!usernameList.contains(user.getId())){
                       identityService.deleteMembership(user.getId(),groupId);
                   }
                }
            }
            if(usernameList!=null&&usernameList.size()>0){
                for (String username:usernameList) {
                    if(!groupUserIds.contains(username)){
                        User userExist = identityService.createUserQuery().userId(username).singleResult();
                        if(userExist==null){
                            userExist = identityService.newUser(username);
                            LoginSysUser sysUser = userClient.findByUsername(username);
                            if(sysUser!=null){
                                userExist.setFirstName(sysUser.getFullname());
                            }
                            identityService.saveUser(userExist);
                        }

                        identityService.createMembership(userExist.getId(),groupId);
                    }
                }
            }
         return new Result(true,StatusCode.OK,"添加组用户成功");

        }catch (Exception e){
            logger.error("**********添加组用户失败reason: "+e.getMessage()+"***********");
            e.printStackTrace();
            return new Result(true,StatusCode.ERROR,"添加组用户失败");
        }

    }

    @Override
    public Result findGroupUsers(String groupId) {
        try {
            ArrayList<String> groupUserIds = new ArrayList<>();
            List<User> list = identityService.createUserQuery().memberOfGroup(groupId).list();
            if(list!=null&&list.size()>0){
                for (User user: list) {
                    groupUserIds.add(user.getId());
                }
            }
            return new Result(true,StatusCode.OK,"查询组用户成功",groupUserIds);
        }catch (Exception e){
            logger.error("**********查询组用户失败reason: "+e.getMessage()+"***********");
            e.printStackTrace();
            return new Result(true,StatusCode.ERROR,"查询组用户失败");

        }

    }

    @Override
    public Result findActUsers(Map map) {
        int pageNum=(int)map.get("pageNum");
        int pageSize=(int)map.get("pageSize");
        int pageStart = (pageNum-1)*pageSize;
        UserQuery userQuery = identityService.createUserQuery().orderByUserFirstName().desc();
        List<User> list = userQuery.list();
        List<User> users = userQuery.listPage(pageStart, pageSize);
        PageInfo<User> pageInfo = new PageInfo<>();
        pageInfo.setTotal(list.size());
        pageInfo.setList(users);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    @Override
    public Result findAllActUsers() {
        ArrayList<String> actUserIds = new ArrayList<>();
        List<User> list = identityService.createUserQuery().list();
        for (User user: list) {
            actUserIds.add(user.getId());
        }
        return new Result(true,StatusCode.OK,"查询所有的流程用户成功",actUserIds);
    }

    @Override
    public Result addActUsers( List<String> usernameList) {
        List<User> list = identityService.createUserQuery().list();
        List<String> userIds = list.parallelStream().map(User::getId).collect(Collectors.toList());
        for (String userId: userIds) {
            if(!usernameList.contains(userId)){
                identityService.deleteUser(userId);
            }
        }
        boolean ret = usernameList.removeAll(userIds);
        Result result = userClient.findUserByList(usernameList);
        if(result.isFlag()){
            List data = (List) result.getData();
            ObjectMapper objectMapper = new ObjectMapper();
            for (Object object: data) {
                SysUser sysUser=objectMapper.convertValue(object, SysUser.class);
                User user = identityService.newUser(sysUser.getUsername());
                user.setFirstName(sysUser.getFullname());
                identityService.saveUser(user);
            }
        }else {
            return new Result(false,StatusCode.ERROR,"服务降级");

        }
        return new Result(true,StatusCode.OK,"添加系统用户成功");
    }

    @Override
    public Result queryByName(Map map) {
        int pageNum=(int)map.get("pageNum");
        int pageSize=(int)map.get("pageSize");
        int pageStart = (pageNum-1)*pageSize;
        String fullname =(String) map.get("fullname");
        UserQuery userQuery = identityService.createUserQuery().userFirstNameLike(fullname).orderByUserFirstName().desc();
        List<User> list = userQuery.list();
        List<User> users = userQuery.listPage(pageStart, pageSize);
        PageInfo<User> pageInfo = new PageInfo<>();
        pageInfo.setTotal(list.size());
        pageInfo.setList(users);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    @Override
    public Result findAllTransferActUsers() {
        List<Map> options = new ArrayList<>();
        List<User> list = identityService.createUserQuery().orderByUserFirstName().desc().list();
        for (User user: list) {
            Map<String, Object> option = new HashMap<>();
            option.put("key" ,user.getId());
            option.put("label",user.getFirstName());
            option.put("disabled",false);
            options.add(option);
        }
        return new Result(true,StatusCode.OK,"Ok",options);
    }


}
