package com.cetc.activiti.service.impl;


import com.cetc.activiti.service.ProcessTaskService;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.common.core.utils.LoginUserUtil;
import com.cetc.model.activiti.MyProcessTask;
import com.cetc.model.admin.LoginSysUser;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProcessTaskServiceImpl implements ProcessTaskService {

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    /**
     * 返回的是下一个流程实例任务的id
     * @param processInstanceId
     * @return
     */
    @Override
    public Result findNextTaskByInstance(String processInstanceId) {

        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        List<String> nextTaskIds = new ArrayList<>();
        for (Task task:taskList) {
            nextTaskIds.add(task.getId());
        }
        return new Result(true, StatusCode.OK,"Ok",nextTaskIds);
    }

    @Override
    public Result findPageTasksByAssign(String processKey,Map<String,Object> map) {
        int pageNum=(int)map.get("pageNum");
        int pageSize=(int)map.get("pageSize");
        int pageStart = (pageNum-1)*pageSize;
        LoginSysUser loginSysUser = LoginUserUtil.getLoginSysUser();
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey(processKey).taskAssignee(loginSysUser.getUsername()).orderByTaskCreateTime().desc();
        List<Task> list = null;
        List<Task> tasks = null;
        if(map.get("title")!=null&&!"".equals(map.get("title"))){
            list=taskQuery.processVariableValueEquals("title",(String)map.get("title")).list();
            tasks = taskQuery.processVariableValueEquals("title",(String)map.get("title")).listPage(pageStart, pageSize);
        }else {
            list = taskQuery.list();
            tasks = taskQuery.listPage(pageStart, pageSize);
        }
        List<MyProcessTask> myProcessTasks = new ArrayList<>();
        for (Task task: tasks) {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            MyProcessTask myProcessTask = new MyProcessTask();
            myProcessTask.setId(task.getId());
            myProcessTask.setTaskName(task.getName());
            myProcessTask.setApplyTitle(processInstance.getName());
            myProcessTask.setCreateTime(task.getCreateTime());
            myProcessTask.setProcessStartTime(processInstance.getStartTime());
            myProcessTask.setExecutionId(task.getExecutionId());

            myProcessTask.setOwner(processInstance.getStartUserId());
            myProcessTask.setBuisnessKey(processInstance.getBusinessKey());
            myProcessTask.setProcessDefinitionId(task.getProcessDefinitionId());
            myProcessTask.setProcessInstanceId(task.getProcessInstanceId());
            myProcessTasks.add(myProcessTask);
        }
        PageInfo info = new PageInfo();
        info.setTotal(list.size());
        info.setList(myProcessTasks);
        return new Result(true,StatusCode.OK,"查询当前任务成功",info);
    }

    @Override
    @Transactional
    public Result handleCheck(String taskId, Map map) {
        try{
            taskService.setVariables(taskId,map);
            taskService.complete(taskId);
            return new Result(true,StatusCode.OK,"操作成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("流程审批失败"+e.getMessage());
        }
    }
}
