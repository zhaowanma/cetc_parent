package com.cetc.project.feign.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;

import com.cetc.project.feign.ProcessClient;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProcessClientImpl implements FallbackFactory<ProcessClient> {
    private static final Logger logger = LoggerFactory.getLogger(ProcessClientImpl.class);

    @Override
    public ProcessClient create(Throwable throwable) {
        return new ProcessClient() {
            @Override
            public Result startProcessInstance(String processDefinitionKey, Long businessKey, Map<String, Object> variables) {
                logger.info("fallback,原因：",throwable);
                return new Result(false, StatusCode.ERROR,"服务降级");
            }

        };
    }
}
