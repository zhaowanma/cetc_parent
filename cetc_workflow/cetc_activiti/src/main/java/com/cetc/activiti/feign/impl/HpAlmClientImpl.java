package com.cetc.activiti.feign.impl;
import com.cetc.activiti.feign.HpAlmClient;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Component
public class HpAlmClientImpl implements HpAlmClient {

    @Override
    public Result createAlmDomain(long codeId) {

        return new Result(false, StatusCode.serviceDeg,"HpAlm服务降级");
    }

    @Override
    public Result createAlmProject(Map map) {
        return new Result(false, StatusCode.serviceDeg,"HpAlm服务降级");
    }
}
