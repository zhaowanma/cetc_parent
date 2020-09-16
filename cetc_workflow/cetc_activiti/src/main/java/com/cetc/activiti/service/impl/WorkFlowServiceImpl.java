package com.cetc.activiti.service.impl;
import com.cetc.activiti.service.WorkFlowService;
import com.cetc.common.core.entity.PageResult;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.activiti.MyDeployment;
import com.cetc.model.activiti.MyProcessDefinition;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class WorkFlowServiceImpl implements WorkFlowService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;


    @Override
    public Result findModelList(PageResult pageResult) {

        int pageStart = (pageResult.getPageNum()-1)*pageResult.getPageSize();
        int pageEnd = pageResult.getPageSize()*pageResult.getPageNum();
        ModelQuery modelQuery = repositoryService
                .createModelQuery()
                .orderByCreateTime()
                .asc();
        PageInfo info = new PageInfo();
        info.setTotal(modelQuery.list().size());
        info.setList(modelQuery.listPage(pageStart,pageEnd));
        return new Result(true, StatusCode.OK,"查询成功",info);
    }

    @Override
    public Result delModel(String id) {
        repositoryService.deleteModel(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @Override
    public Result publishModel(String id) {
        try {
            Model model = repositoryService.getModel(id);
            byte[] bytes = repositoryService.getModelEditorSource(model.getId());
            if(bytes == null){
                return new Result(false,StatusCode.ERROR,"模型数据为空，请先设计流程并成功保存，再进行发布");
            }
            JsonNode modelNode = new ObjectMapper().readTree(bytes);
            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            Deployment deployment = repositoryService.createDeployment()
                    .name(model.getName())
                    .addBpmnModel(model.getKey() + ".bpmn20.xml", bpmnModel)
                    .deploy();
             model.setDeploymentId(deployment.getId());
             repositoryService.saveModel(model);
             return  new Result(true,StatusCode.OK,"发布成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"发布失败");
        }
    }

    @Override
    public Result revokePublish(String id) {
        Model model = repositoryService.getModel(id);
        if(null != model){
            repositoryService.deleteDeployment(model.getDeploymentId(),true);
            return  new Result(true,StatusCode.OK,"取消发布成功");
        }else {
            return new Result(false,StatusCode.ERROR,"取消发布失败");
        }
    }
    @Override
    public Result findDeploymentList(PageResult pageResult) {
        int pageStart = (pageResult.getPageNum()-1)*pageResult.getPageSize();
        int pageEnd = pageResult.getPageSize()*pageResult.getPageNum();
        List<MyDeployment> myDeploymentList = new ArrayList<>();
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery().orderByDeploymenTime().asc();
        List<Deployment> list = deploymentQuery.listPage(pageStart, pageEnd);
        for (Deployment deployment : list) {
            MyDeployment myDeployment = new MyDeployment();
            myDeployment.setId(deployment.getId());
            myDeployment.setName(deployment.getName());
            myDeployment.setDeploymentTime(deployment.getDeploymentTime());
            myDeploymentList.add(myDeployment);
        }
        PageInfo info = new PageInfo();
        info.setTotal(deploymentQuery.list().size());
        info.setList(myDeploymentList);
        return new Result(true,StatusCode.OK,"查询成功",info);
    }

    @Override
    public Result deldeployment(String id) {
        repositoryService.deleteDeployment(id,true);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @Override
    public Result processDefinitionList(PageResult pageResult) {
        int pageStart = (pageResult.getPageNum()-1)*pageResult.getPageSize();
        int pageEnd = pageResult.getPageSize()*pageResult.getPageNum();
        List<MyProcessDefinition> myProcessDefinitionList = new ArrayList<>();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion();
        List<ProcessDefinition> processDefinitions = processDefinitionQuery.desc().listPage(pageStart, pageEnd);
        for (ProcessDefinition processDefinition : processDefinitions) {
            MyProcessDefinition myProcessDefinition = new MyProcessDefinition();
            myProcessDefinition.setId(processDefinition.getId());
            myProcessDefinition.setKey(processDefinition.getKey());
            myProcessDefinition.setName(processDefinition.getName());
            myProcessDefinition.setDescription(processDefinition.getDescription());
            myProcessDefinition.setVersion(processDefinition.getVersion());
            myProcessDefinition.setResourceName(processDefinition.getResourceName());
            myProcessDefinition.setDiagramResourceName(processDefinition.getDiagramResourceName());
            myProcessDefinition.setDeploymentId(processDefinition.getDeploymentId());
            myProcessDefinitionList.add(myProcessDefinition);
        }
        PageInfo info = new PageInfo();
        info.setTotal(processDefinitionQuery.list().size());
        info.setList(myProcessDefinitionList);
        return new Result(true,StatusCode.OK,"查询成功",info);
    }



    @Override
    public Result viewImage(String definitionId) {
        //获取BPMN模型对象
        BpmnModel bpmnModel = repositoryService.getBpmnModel(definitionId);
        //使用宋体
       String fontName = "courier";
        //获取流程实例当前的节点，需要高亮显示
        List<String> currentActs = Collections.EMPTY_LIST;
        InputStream inputStream = processEngine.getProcessEngineConfiguration()
                .getProcessDiagramGenerator()
                .generateDiagram(bpmnModel, "png", currentActs, new ArrayList<String>(),
                        fontName, fontName, fontName, null, 1.0);

        try {
            //使用base64方式返回前端显示图片
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i;
            while ((i=inputStream.read())!=-1){
                byteArrayOutputStream.write(i);
            }
            BASE64Encoder encoder = new BASE64Encoder();
            String baseimage = "data:image/png;base64,"+ encoder.encode(byteArrayOutputStream.toByteArray());
            return new Result(true,StatusCode.OK,"",baseimage);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"","");
        }


    }




}
