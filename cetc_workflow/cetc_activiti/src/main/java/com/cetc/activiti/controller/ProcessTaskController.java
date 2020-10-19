package com.cetc.activiti.controller;

import com.cetc.activiti.service.ProcessTaskService;
import com.cetc.common.core.entity.Result;
import com.cetc.model.log.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("processTask")
@Api(description = "流程任务控制层类")
public class ProcessTaskController {

    @Autowired
    private ProcessTaskService processTaskService;

    @GetMapping("findNextTaskByInstance/{processInstanceId}")
    @ApiOperation("根据流程实例id查询流程任务接口")
    public Result findNextTaskByInstance(@PathVariable String processInstanceId){
        return processTaskService.findNextTaskByInstance(processInstanceId);
    }


    @PostMapping("findTasksByAssign/{processKey}")
    @ApiOperation("根据用户名分页查询当前流程任务接口")
    public Result findTasksByAssign(@PathVariable String processKey,@RequestBody Map<String,Object> map){
        return processTaskService.findPageTasksByAssign(processKey,map);
    }

   @PostMapping("handleCheck/{taskId}")
   @ApiOperation("根据任务id完成任务接口")
   @LogAnnotation(module = "完成审批流程")
    public Result handleCheck(@PathVariable String taskId, @RequestBody Map map){
        return processTaskService.handleCheck(taskId,map);
    }



}
