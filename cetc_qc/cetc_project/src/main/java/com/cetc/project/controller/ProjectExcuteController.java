package com.cetc.project.controller;

import com.cetc.common.core.entity.Result;
import com.cetc.model.log.LogAnnotation;
import com.cetc.model.project.ProjectExcute;
import com.cetc.project.entities.SearchProjectExcute;
import com.cetc.project.service.ProjectExcuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 测试执行
 */
@RestController
@RequestMapping("testExcute")
public class ProjectExcuteController {

    @Autowired
    private ProjectExcuteService projectExcuteService;
    @PostMapping("addProjectExcute")
    @LogAnnotation(module = "添加测试执行")
    public Result addProjectExcute(@RequestBody ProjectExcute projectExcute){
         return projectExcuteService.addProjectExcute(projectExcute);
    }

    @PostMapping("pageList")
    public Result pageList(@RequestBody SearchProjectExcute searchExcute){
        return projectExcuteService.findPageExcuteByProject(searchExcute);
    }

    @DeleteMapping("deleteExcute/{id}")
    @LogAnnotation(module = "删除测试执行")
    public  Result deleteExcute(@PathVariable Long id){
        return projectExcuteService.deleteProjectExcute(id);
    }

    @PostMapping("updateProjectExcute")
    @LogAnnotation(module = "更新测试执行")
    public Result updateProjectExcute(@RequestBody ProjectExcute projectExcute){
        return projectExcuteService.updateProjectExcute(projectExcute);
    }
}
