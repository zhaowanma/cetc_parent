package com.cetc.activiti.controller;

import com.cetc.activiti.service.ProcessHistoryService;
import com.cetc.common.core.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RequestMapping("processHistory")
@RestController
@Api(description = "历史流程控制层类")
public class ProcessHistoryController {

    @Autowired
    private ProcessHistoryService processHistoryService;

    @PostMapping("findInstanceByStartUser/{processKey}")
    @ApiOperation("根据用户名分页查询流程信息接口")
    public Result findInstanceByStartUser(@PathVariable String processKey, @RequestBody Map map){
        return  processHistoryService.findInstanceByStartUser(processKey,map);
    }

    @GetMapping("findExecByInstanceId/{processInstanceId}")
    @ApiOperation("根据流程实例id查询流程执行流接口")
    public Result findExecByInstanceId(@PathVariable String processInstanceId){
        return processHistoryService.findExecByInstanceId(processInstanceId);
    }
    @GetMapping("findExecByBussinessId/{processDefinitionKey}/{bussinessId}")
    @ApiOperation("根据业务id查询流程执行流接口")
    public Result findExecByBussinessId(@PathVariable String processDefinitionKey,@PathVariable Long bussinessId){
        return processHistoryService.findExecByBusinessId(processDefinitionKey,bussinessId);
    }

    @PostMapping("findHistoryTasksByAssign/{processKey}/{name}")
    @ApiOperation("根据用户名分页查询当前流程任务接口")
    public Result findHistoryTasksByAssign(@PathVariable String processKey,@PathVariable String name,@RequestBody Map<String,Object> map){
        return processHistoryService.findTaskByAssign(processKey,name,map);
    }

    @GetMapping("findHistoryTaskVariables/{taskId}")
    public Result findHistoryTaskVariables(@PathVariable String taskId){
        return processHistoryService.checkHistoryTaskVariable(taskId);
    }


}
