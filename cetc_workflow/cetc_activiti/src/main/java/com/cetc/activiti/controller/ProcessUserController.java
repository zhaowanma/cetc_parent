package com.cetc.activiti.controller;

import com.cetc.activiti.service.ProcessUserService;
import com.cetc.common.core.entity.PageResult;
import com.cetc.common.core.entity.Result;
import com.cetc.model.log.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("processUser")
@Api(description = "流程用户控制层类")
public class ProcessUserController {
    @Autowired
    private ProcessUserService processUserService;

    @PostMapping("findGroups")
    @ApiOperation("分页查询流程用户组接口")
    public Result findGroups(@RequestBody PageResult pageResult){
      return processUserService.findGroups(pageResult);
    }

    @PostMapping("createGroup")
    @ApiOperation("新增流程用户组接口")
    @LogAnnotation(module = "新增流程用户组")
    public Result createGroup(@RequestBody Map map){
        return processUserService.createGroup(map);
    }

    @GetMapping("findGroupById/{id}")
    @ApiOperation("根据流程用户组id查询流程用户组接口")
    public Result findGroupById(@PathVariable String id){
        return processUserService.findGroupById(id);
    }

    @PutMapping("updateGroupById")
    @ApiOperation("根据流程用户组id修改流程用户组接口")
    @LogAnnotation(module = "修改流程用户组")
    public Result updateGroupById(@RequestBody Map map){
        return processUserService.updateGroupById(map);
    }

    @DeleteMapping("deleteGroupById/{id}")
    @ApiOperation("根据流程用户组id删除流程用户组接口")
    @LogAnnotation(module = "删除流程用户组")
    public Result deleteGroupById(@PathVariable String id){
        return processUserService.deleteGroupById(id);
    }
    @PostMapping("addUserGroup/{groupId}")
    @ApiOperation("添加流程用户组用户接口")
    @LogAnnotation(module = "添加用户组用户")
    public Result addUserGroup(@PathVariable String groupId, @RequestBody List<String> usernameList){
        return processUserService.createMemberShip(groupId,usernameList);
    }
    @GetMapping("findGroupUsers/{groupId}")
    @ApiOperation("查询流程用户组所有用户接口")
    public Result findGroupUsers(@PathVariable String groupId){
        return processUserService.findGroupUsers(groupId);
    }

    @PostMapping("findPageActUsers")
    @ApiOperation("分页查询流程用户接口")
    public Result findPageActUsers(@RequestBody Map map){
        return processUserService.findActUsers(map);
    }

    @GetMapping("findAllActUsers")
    @ApiOperation("查询所有流程用户接口")
    public Result findAllActUsers(){
        return  processUserService.findAllActUsers();
    }

    @PostMapping("addActUsers")
    @ApiOperation("新增流程用户接口")
    @LogAnnotation(module = "新增流程用户")
    public Result addActUsers(@RequestBody List<String> usernameList){
        return processUserService.addActUsers(usernameList);
    }

    @PostMapping("queryByName")
    @ApiOperation("根据用户姓名查询流程用户接口")
    public Result queryByName(@RequestBody Map map){
        String fullname =(String) map.get("fullname");
        if("".equals(fullname)&&fullname!=null){
            return processUserService.findActUsers(map);
        }else {
            return processUserService.queryByName(map);
        }
    }
    @GetMapping("findAllTransferActUsers")
    @ApiOperation("查询所有流程用户接口，返回transfer格式数据")
    public Result findAllTransferActUsers(){
        return processUserService.findAllTransferActUsers();
    }

}
