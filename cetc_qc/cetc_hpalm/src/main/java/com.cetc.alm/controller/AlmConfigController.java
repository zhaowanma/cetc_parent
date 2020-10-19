package com.cetc.alm.controller;

import com.cetc.alm.service.AlmConfigService;
import com.cetc.common.core.entity.Result;
import com.cetc.model.hpalm.AlmConfig;
import com.cetc.model.hpalm.AlmSiteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("almConfig")
public class AlmConfigController {

    @Autowired
    private AlmConfigService almConfigService;

    @PostMapping("save")
    public Result save(@RequestBody AlmConfig almConfig){
         return almConfigService.saveAlmConfig(almConfig);
    }

    @GetMapping("findAlmConfig")
    public Result findAlmConfigById(){
        return almConfigService.findAlmConfig();
    }


    @PostMapping("saveAlmSiteConfig")
    public Result saveAlmSiteConfig(@RequestBody AlmSiteConfig almSiteConfig){
         return almConfigService.saveAlmSiteConfig(almSiteConfig);
    }

    @GetMapping("findAlmSiteConfig")
    public Result findAlmSiteConfig(){
        return almConfigService.findAlmSiteConfig();
    }


}
