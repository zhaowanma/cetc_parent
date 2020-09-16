package com.cetc.project.controller;

import com.cetc.common.core.entity.Result;
import com.cetc.model.log.LogAnnotation;
import com.cetc.model.project.CodeExamination;
import com.cetc.project.entities.SearchExamination;
import com.cetc.project.service.CodeExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 代码审查任务分配
 */
@RestController
@RequestMapping("/examination")
public class CodeExaminationController {

    @Autowired
    private CodeExaminationService examinationService;
    @PostMapping("add")
    @LogAnnotation(module = "添加代码审查任务分配")
    public Result add(@RequestBody CodeExamination spotCheck){
        return examinationService.addSpotCheck(spotCheck);
    }

    @PostMapping("searchPage")
    public Result searchPage(@RequestBody SearchExamination searchSpotCheck){
        return examinationService.pageList(searchSpotCheck);
    }
    @DeleteMapping("delete/{id}")
    @LogAnnotation(module = "删除代码审查任务分配")
    public Result deleteOne(@PathVariable Long id){
        return examinationService.deleteOne(id);
    }
    @PostMapping("update")
    @LogAnnotation(module = "更新代码审查任务分配")
    public Result update(@RequestBody CodeExamination spotCheck){
        return examinationService.updateSpotCheck(spotCheck);
    }

}
