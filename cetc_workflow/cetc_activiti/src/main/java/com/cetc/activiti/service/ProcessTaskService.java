package com.cetc.activiti.service;

import com.cetc.common.core.entity.Result;
import java.util.Map;

public interface ProcessTaskService {

     Result findNextTaskByInstance(String processInstanceId);

     Result findPageTasksByAssign(String processKey,Map<String,Object> map);

     Result handleCheck(String taskId, Map map);


}
