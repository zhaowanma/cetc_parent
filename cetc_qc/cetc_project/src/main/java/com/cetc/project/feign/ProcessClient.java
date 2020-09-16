package com.cetc.project.feign;

import com.cetc.common.core.entity.Result;
import com.cetc.project.feign.impl.ProcessClientImpl;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value = "cetc-activiti",fallbackFactory = ProcessClientImpl.class)
public interface ProcessClient {

    @PostMapping("/processInstance/startProcessInstance/{processDefinitionKey}/{businessKey}")
    @Hmily
     Result startProcessInstance(@PathVariable String processDefinitionKey, @PathVariable Long businessKey, @RequestBody Map<String, Object> variables);

}
