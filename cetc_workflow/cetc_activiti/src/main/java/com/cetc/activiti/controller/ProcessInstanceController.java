package com.cetc.activiti.controller;

import com.cetc.activiti.service.ProcessInstanceService;
import com.cetc.common.core.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("processInstance")
@Api(description = "流程实例控制层类")
public class ProcessInstanceController {

    @Autowired
    private ProcessInstanceService processInstanceService;


    @PostMapping("startProcessInstance/{processDefinitionKey}/{businessKey}")
    @ApiOperation("开启流程实例接口")
    public Result  startProcessInstance(@PathVariable String processDefinitionKey, @PathVariable Long businessKey, @RequestBody Map<String,Object> variables){
       return processInstanceService.startProcessInstance(processDefinitionKey,businessKey,variables);
    }

    @GetMapping("findAllProcessInstance/{processDefinitionKey}")
    @ApiOperation("根据流程key查询所有的流程实例接口")
    public Result findAllProcessInstance(@PathVariable String processDefinitionKey){
        return processInstanceService.findAllProcessInstance(processDefinitionKey);
    }

    @GetMapping("generateImage/{processInstanceId}")
    @ApiOperation("根据流程实例id生成当前流程实例的流程图接口")
    public Result generateImage(@PathVariable String processInstanceId){
        return processInstanceService.genereateProcessImage(processInstanceId);
    }
    @GetMapping("generateImage/{processDefinitionKey}/{bussinessId}")
    public Result generateImageByBussinessId(@PathVariable String processDefinitionKey,@PathVariable Long bussinessId){
        return processInstanceService.genereateProcessImageByBussinessKey(processDefinitionKey,bussinessId);
    }

}
