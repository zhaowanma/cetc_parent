package com.cetc.activiti.autoTask;


import com.cetc.activiti.feign.ProjectClient;
import com.cetc.common.core.entity.Result;
import com.cetc.model.project.Code;
import com.cetc.model.project.Project;
import org.activiti.engine.delegate.DelegateExecution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("autoTaskService")
public class AutoTaskService {

    private static final Logger logger=LoggerFactory.getLogger(AutoTaskService.class);
    @Autowired
    private ProjectClient projectClient;

    @Transactional
    public void fileTransfer(DelegateExecution execution) {
        System.out.println("文件导入完成了");
    }

    @Transactional
    public void sendMsg(DelegateExecution execution) {
        System.out.println("通知发送完毕");
    }

    @Transactional
    public void updateApply(DelegateExecution execution) {
        try {
            String id = execution.getProcessInstanceBusinessKey();
            Code code = new Code();
            code.setId(Long.parseLong(id));
            code.setStatus(true);
            Result result = projectClient.updateCodeStatus(code);
            if(!result.isFlag()){
                throw new RuntimeException("修改令号状态远程调用失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("修改令号状态失败" +e.getMessage());
            throw new RuntimeException("修改令号状态失败");

        }

    }

    @Transactional
    public void updateProject(DelegateExecution execution) {
        try {
            String id = execution.getProcessInstanceBusinessKey();
            Map<String, Object> variables = execution.getVariables();
            Project project = new Project();
            if(variables!=null){
                String almDomain =(String)variables.get("almDomain");
                String almProject =(String)variables.get("almProject");
                project.setId(Long.valueOf(id));
                project.setAlmDomainName(almDomain);
                project.setAlmProjectName(almProject);
            }
            project.setStatus(true);
            Result result = projectClient.updateProjectStatus(project);
            if(!result.isFlag()){
                logger.error("autoTaskService：修改项目状态远程调用失败内");
                throw new RuntimeException("修改项目状态远程调用失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("修改项目状态失败" +e.getMessage());
            throw new RuntimeException("修改项目状态失败");
        }


    }
    @Transactional
    public void deleteProject(DelegateExecution execution) {
        try{
            String id = execution.getProcessInstanceBusinessKey();
            Result result = projectClient.deleteProjectById(Long.valueOf(id));
            if(!result.isFlag()){
                logger.error("autoTaskService：删除项目远程调用失败内");
                throw new RuntimeException("删除项目远程调用失败");
            }
        }catch (Exception e){
              e.printStackTrace();
            logger.error("autoTaskService：删除项目失败");
            throw new RuntimeException("删除项目失败");
        }
    }

}