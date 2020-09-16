package com.cetc.gateway.feign.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.gateway.feign.DicClient;
import org.springframework.stereotype.Component;

@Component
public class DicClientImpl implements DicClient {
    @Override
    public Result queryByKey(String key) {
        return new Result(false, StatusCode.ERROR,"查询系统参数失败，服务降级");
    }
}
