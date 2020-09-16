package com.cetc.alm.controller;

import com.cetc.alm.service.AnalysicService;
import com.cetc.alm.service.ProjectService;
import com.cetc.common.core.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("almProject")
public class AlmProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AnalysicService analysicService;

    @GetMapping("findProjectsByDomainName/{domainName}")
  public Result findProjectsByDomainName(@PathVariable String domainName){
     return projectService.findProjectsByDomainName(domainName);
  }

  @GetMapping("findProjectAnalysic1/{id}/{refresh}")
  public Result findProjectAnalysic1(@PathVariable long id,@PathVariable Boolean refresh){
        return analysicService.findProjectDataCount(id,refresh);
  }

    @GetMapping("findProjectAnalysic2/{id}/{refresh}")
    public Result findProjectAnalysic2(@PathVariable long id,@PathVariable Boolean refresh){
        return analysicService.testCaseCount(id,refresh);
    }

    @GetMapping("findProjectAnalysic3/{id}/{refresh}")
    public Result findProjectAnalysic3(@PathVariable long id,@PathVariable Boolean refresh){
        return analysicService.defectCount(id,refresh);
    }

    @GetMapping("findProjectAnalysic4/{id}/{refresh}")
    public Result findProjectAnalysic4(@PathVariable long id,@PathVariable Boolean refresh){
        return analysicService.testCaseTypeCount(id,refresh);
    }
}
