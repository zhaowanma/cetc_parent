package com.cetc.log.controller;

import com.cetc.common.core.entity.PageResult;
import com.cetc.common.core.entity.Result;

import com.cetc.log.service.LogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Api(tags = "日志控制层类")
public class LogController {

    @Autowired
    private LogService logService;


    @PostMapping(value = "/queryPageLogs")
    @ApiOperation("查询日志接口")
    public Result findPageLogs(@RequestBody Map<String, Object> params){
      return logService.queryPageslogs(params);
    }


}
