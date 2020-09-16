package com.cetc.activiti.service;

import com.cetc.common.core.entity.Result;

import java.util.Map;

public interface ProcessHistoryService {

     Result findInstanceByStartUser(String processKey,Map map);

     Result findExecByInstanceId(String instanceId);

     Result findExecByBusinessId(String processKey,long bussinessId);

     Result findTaskByAssign(String processKey,String name,Map<String,Object> map);

     Result checkHistoryTaskVariable(String taskId);


}
