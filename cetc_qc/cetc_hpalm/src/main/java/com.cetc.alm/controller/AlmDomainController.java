package com.cetc.alm.controller;

import com.cetc.alm.service.DomainService;
import com.cetc.common.core.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("almDomain")
public class AlmDomainController {

    @Autowired
    private DomainService domainService;

    @GetMapping("findAlmDomains")
    public Result findAlmDomains(){
            return domainService.findAlmDomains();
    }


}
