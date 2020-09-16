package com.cetc.activiti.controller;

import com.cetc.activiti.service.WorkFlowService;
import com.cetc.common.core.entity.PageResult;
import com.cetc.common.core.entity.Result;
import com.cetc.model.log.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("activiti")
@Api(description = "流程模型控制层类")
public class WorkFlowController {
    @Autowired
    private WorkFlowService workFlowService;


    @PostMapping("findModelList")
    @ApiOperation("分页查询所有流程模型接口")
    public Result findModelList(@RequestBody PageResult pageResult){
       return workFlowService.findModelList(pageResult);

    }

    @DeleteMapping("delModel/{id}")
    @ApiOperation("根据模型id删除流程模型接口")
    @LogAnnotation(module = "删除流程模型")
    public Result delModel(@PathVariable String id){
        return workFlowService.delModel(id);
    }

    @GetMapping("publishModel/{id}")
    @ApiOperation("根据模型id发布流程模型接口")
    @LogAnnotation(module = "发布流程模型")
    public Result publishModel(@PathVariable String id){
        return workFlowService.publishModel(id);
    }

    @GetMapping("revokePublish/{id}")
    @ApiOperation("根据模型id撤销流程模型接口")
    @LogAnnotation(module = "撤销流程模型")
    public Result revokePublish(@PathVariable String id){
        return  workFlowService.revokePublish(id);
    }
    @PostMapping("deploymentList")
    @ApiOperation("分页查询所有的流程发布信息接口")
    public Result deploymentList(@RequestBody PageResult pageResult){
        return workFlowService.findDeploymentList(pageResult);
    }

    @DeleteMapping("deldeployment/{deploymentId}")
    @ApiOperation("根据流程发布信息id删除流程发布信息接口")
    public Result deldeployment(@PathVariable String deploymentId){
        return workFlowService.deldeployment(deploymentId);
    }

    @PostMapping("processDefinitionList")
    @ApiOperation("分页查询流程定义信息接口")
    public Result processDefinitionList(@RequestBody PageResult pageResult){
        return workFlowService.processDefinitionList(pageResult);
    }
    @GetMapping("viewImage/{definitionId}")
    @ApiOperation("根据流程定义id查询流程图接口")
    public Result viewImage(@PathVariable String definitionId){
      return workFlowService.viewImage(definitionId);
    }



}
