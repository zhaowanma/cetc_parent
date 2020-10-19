package com.cetc.activiti.feign;

import com.cetc.activiti.feign.impl.HpAlmClientImpl;
import com.cetc.activiti.feign.impl.UserClientImpl;
import com.cetc.common.core.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value="cetc-hpalm",fallback = HpAlmClientImpl.class )
public interface HpAlmClient {

    @GetMapping("almDomain/createAlmDomains/{codeId}")
    Result createAlmDomain(@PathVariable long codeId);

    @PostMapping("almProject/createAlmProject")
    Result createAlmProject(@RequestBody Map map);

}
