package com.cetc.project.controller;

import com.cetc.common.core.entity.Result;
import com.cetc.model.project.ProjectMonthCommit;
import com.cetc.project.service.ProjectMonthCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("projectMonthCommit")
public class ProjectMonthCommitController {

    @Autowired
    private ProjectMonthCommitService projectMonthCommitService;

    @PostMapping("add")
    public Result add(@RequestBody ProjectMonthCommit projectMonthCommit){
          return projectMonthCommitService.save(projectMonthCommit);
    }

    @GetMapping("findAll/{id}")
    public Result findAll(@PathVariable Long id){
        return projectMonthCommitService.findAll(id);
    }

}
