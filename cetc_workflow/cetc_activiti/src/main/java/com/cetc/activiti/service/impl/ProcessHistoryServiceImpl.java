package com.cetc.activiti.service.impl;

import com.cetc.activiti.feign.UserClient;
import com.cetc.activiti.service.ProcessHistoryService;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.common.core.utils.LoginUserUtil;
import com.cetc.model.activiti.MyProcessInstance;
import com.cetc.model.activiti.MyProcessTask;
import com.cetc.model.admin.LoginSysUser;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.*;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProcessHistoryServiceImpl implements ProcessHistoryService {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private RuntimeService runtimeService;

    /**
     * 查询该登录用户下的所有实例
     * @param processKey
     * @param map
     * @return
     */

    @Override
    public Result findInstanceByStartUser(String processKey,Map map) {
        int pageNum=(int)map.get("pageNum");
        int pageSize=(int)map.get("pageSize");
        int pageStart = (pageNum-1)*pageSize;

        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(processKey)
                .startedBy(LoginUserUtil.getLoginSysUser().getUsername()).orderByProcessInstanceStartTime().desc();
        List<HistoricProcessInstance> list = query.list();
        List<HistoricProcessInstance> historicProcessInstanceList = query.listPage(pageStart,pageSize);

        List<MyProcessInstance> instanceList = new ArrayList<>();
        for (HistoricProcessInstance historicProcessInstance: historicProcessInstanceList) {
            MyProcessInstance myProcessInstance = new MyProcessInstance();
            myProcessInstance.setProcessInstanceId(historicProcessInstance.getId());
            myProcessInstance.setBusinessKey(historicProcessInstance.getBusinessKey());
            if(historicProcessInstance.getEndTime()!=null) {
                myProcessInstance.setEndTime(historicProcessInstance.getEndTime());
            }
            myProcessInstance.setName(historicProcessInstance.getName());
            myProcessInstance.setProcessDefinitionKey(historicProcessInstance.getProcessDefinitionKey());
            myProcessInstance.setStartTime(historicProcessInstance.getStartTime());
            if(historicProcessInstance.getEndTime()==null) {
                List<Task> taskList = taskService.createTaskQuery().processInstanceId(historicProcessInstance.getId()).list();
                if (taskList != null && taskList.size() > 0) {
                    User checkUser = identityService.createUserQuery().userId(taskList.get(0).getAssignee()).singleResult();
                    myProcessInstance.setCurrentChecker(checkUser.getFirstName());
                }
            }else {
                myProcessInstance.setCurrentChecker("流程已闭合");
            }
            instanceList.add(myProcessInstance);
        }
        PageInfo<MyProcessInstance> pageInfo = new PageInfo<>();
        pageInfo.setList(instanceList);
        pageInfo.setTotal(list.size());
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    @Override
    public Result findExecByInstanceId(String instanceId) {
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(instanceId).orderByHistoricActivityInstanceStartTime().asc().list();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Object> execInfos = new ArrayList<>();
        if(list!=null&&list.size()>0){
            for (int i=0;i<list.size();i++) {
                String startTime = sdf.format(list.get(i).getStartTime());
                Map<String, String> execInfo = new HashMap<>();
                execInfo.put("startTime",startTime);
                execInfo.put("eventName",list.get(i).getActivityName());
                execInfo.put("eventType",list.get(i).getActivityType());
                if(i<=list.size()-2||"endEvent".equals(list.get(i).getActivityType())) {
                    execInfo.put("color", "#67C23A");
                }else{
                    execInfo.put("color", "#E6A23C");
                }
                if(list.get(i).getAssignee()!=null){
                    String assignee = list.get(i).getAssignee();
                    LoginSysUser sysUser = userClient.findByUsername(assignee);
                   // User sysUser = identityService.createUserQuery().userId(list.get(i).getAssignee()).singleResult();
                    execInfo.put("assign",sysUser.getFullname());
                }else {
                    execInfo.put("assign",null) ;
                }
                execInfos.add(execInfo);
            }
        }

        return new Result(true,StatusCode.OK,"Ok",execInfos);
    }

    @Override
    public Result findExecByBusinessId(String processKey, long bussinessId) {
        List<HistoricProcessInstance> list1 = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(processKey).processInstanceBusinessKey(String.valueOf(bussinessId)).orderByProcessInstanceStartTime().desc().list();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(list1.get(0).getId()).orderByHistoricActivityInstanceStartTime().asc().list();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Object> execInfos = new ArrayList<>();
        if(list!=null&&list.size()>0){
            for (int i=0;i<list.size();i++) {
                String startTime = sdf.format(list.get(i).getStartTime());
                Map<String, String> execInfo = new HashMap<>();
                execInfo.put("startTime",startTime);
                String eventName=list.get(i).getActivityName();
                if(eventName!=null&&eventName.indexOf("ALM域")>-1) {
                    List<HistoricDetail> list3 = historyService.createHistoricDetailQuery().processInstanceId(list1.get(0).getId()).list();
                    if (list3 != null && list3.size() > 0) {
                        for (HistoricDetail historicDetail : list3) {
                            HistoricVariableUpdate variable = (HistoricVariableUpdate) historicDetail;
                            if ("comments".equals(variable.getVariableName())) {
                                if (variable.getValue() != null) {
                                    eventName = eventName + "   -回执说明-：" + variable.getValue();

                                } else {
                                    eventName = eventName + "   -回执说明-：无";

                                }
                            }
                        }
                    }
                }

                execInfo.put("eventName",eventName);
                execInfo.put("eventType",list.get(i).getActivityType());
                if(i<=list.size()-2||"endEvent".equals(list.get(i).getActivityType())) {
                    execInfo.put("color", "#67C23A");
                }else{
                    execInfo.put("color", "#E6A23C");
                }
                if(list.get(i).getAssignee()!=null){
                    String assignee = list.get(i).getAssignee();
                    LoginSysUser sysUser = userClient.findByUsername(assignee);
                    execInfo.put("assign",sysUser.getFullname());
                }else {
                    execInfo.put("assign",null) ;
                }

                execInfos.add(execInfo);

            }
        }

        return new Result(true,StatusCode.OK,"Ok",execInfos);
    }

    @Override
    public Result findTaskByAssign(String processKey,String name,Map<String,Object> map) {
        LoginSysUser loginSysUser = LoginUserUtil.getLoginSysUser();
        int pageNum=(int)map.get("pageNum");
        int pageSize=(int)map.get("pageSize");
        int pageStart = (pageNum-1)*pageSize;
        HistoricTaskInstanceQuery taskQuery = historyService.createHistoricTaskInstanceQuery().processDefinitionKey(processKey).taskName(name).finished().taskAssignee(loginSysUser.getUsername()).orderByTaskCreateTime().desc();
        List<HistoricTaskInstance> list = null;
        List<HistoricTaskInstance> tasks = null;
        if(map.get("title")!=null&&!"".equals(map.get("title"))){
            list=taskQuery.processVariableValueEquals("title",(String)map.get("title")).list();
            tasks = taskQuery.processVariableValueEquals("title",(String)map.get("title")).listPage(pageStart, pageSize);
        }else {
            list = taskQuery.list();
            tasks = taskQuery.listPage(pageStart, pageSize);
        }
        List<MyProcessTask> myProcessTasks = new ArrayList<>();
        for (HistoricTaskInstance task: tasks) {
            final HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            MyProcessTask myProcessTask = new MyProcessTask();
            myProcessTask.setId(task.getId());
            myProcessTask.setTaskName(task.getName());
            myProcessTask.setApplyTitle(processInstance.getName());
            myProcessTask.setCreateTime(task.getCreateTime());
            myProcessTask.setProcessStartTime(processInstance.getStartTime());
            myProcessTask.setEndTime(processInstance.getEndTime());
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
    public Result checkHistoryTaskVariable(String taskId) {
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        HistoricDetailQuery historicDetailQuery = historyService.createHistoricDetailQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).variableUpdates();
        List<HistoricDetail> list = historicDetailQuery.list();
        return new Result(true,StatusCode.OK,"查询成功",list);

    }




}
