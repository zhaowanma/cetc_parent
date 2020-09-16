package com.cetc.activiti.service;

import com.cetc.common.core.entity.Result;
import org.dromara.hmily.annotation.Hmily;

import java.util.Map;

public interface ProcessInstanceService {

    @Hmily
     Result startProcessInstance(String processDefinitionKey, Long businessKey, Map<String,Object> variables);

     Result findAllProcessInstance(String processDefinitionKey);

     Result genereateProcessImage(String processInstanceId);

     Result genereateProcessImageByBussinessKey(String processDefinitionKey,Long businessKey);

}
