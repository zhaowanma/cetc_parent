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

    @PostMapping("update")
    public Result update(@RequestBody ProjectMonthCommit projectMonthCommit){
        return projectMonthCommitService.update(projectMonthCommit);
    }

    @GetMapping("delete/{id}")
    public Result delete(@PathVariable Long id){
        return projectMonthCommitService.delete(id);
    }

    @GetMapping("findById/{id}")
    public Result findById(@PathVariable Long id){
        return projectMonthCommitService.findById(id);
    }

}
