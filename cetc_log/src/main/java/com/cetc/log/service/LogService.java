package com.cetc.log.service;

import com.cetc.common.core.entity.PageResult;
import com.cetc.common.core.entity.Result;
import com.cetc.model.log.Log;

import java.util.Map;


public interface LogService {
    void save(Log log);

    Result queryPageslogs(Map<String,Object> params);
}
