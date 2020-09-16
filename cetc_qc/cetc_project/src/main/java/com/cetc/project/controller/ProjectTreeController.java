package com.cetc.project.controller;

import com.cetc.common.core.entity.Result;
import com.cetc.model.project.TreeData;
import com.cetc.project.service.ProjectTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;


@RestController
public class ProjectTreeController {

    @Autowired
    private ProjectTreeService projectTreeService;

    @GetMapping("findProjectTree")
    public Result findProjectTree(){
        return projectTreeService.findTreeData();
    }


    @PostMapping("findMyProjectTree")
    public Result findMyProjectTree(@RequestBody List<TreeData> projectList){
        return projectTreeService.findMyTreeData(projectList);
    }
}
