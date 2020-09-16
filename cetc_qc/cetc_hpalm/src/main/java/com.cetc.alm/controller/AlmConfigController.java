package com.cetc.alm.controller;

import com.cetc.alm.service.AlmConfigService;
import com.cetc.common.core.entity.Result;
import com.cetc.model.hpalm.AlmConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("almConfig")
public class AlmConfigController {

    @Autowired
    private AlmConfigService almConfigService;

    @RequestMapping("save")
    public Result save(@RequestBody AlmConfig almConfig){
         return almConfigService.saveAlmConfig(almConfig);
    }

    @RequestMapping("findAlmConfig")
    public Result findAlmConfigById(){
        return almConfigService.findAlmConfig();
    }
}
