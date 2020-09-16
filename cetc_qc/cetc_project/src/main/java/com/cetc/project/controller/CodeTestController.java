package com.cetc.project.controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.cetc.common.core.entity.Result;
import com.cetc.model.project.CodeTest;
import com.cetc.project.entities.SearchCodeTest;
import com.cetc.project.service.CodeTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/codetest")
public class CodeTestController {
    @Autowired
    private CodeTestService codeTestService;

    @PostMapping("add")
    public Result add(@RequestBody CodeTest codeTest){
       return codeTestService.addCodeTest(codeTest);
    }

    @PostMapping("pageList")
    public Result pageList(@RequestBody SearchCodeTest searchCodeTest){
        return codeTestService.pageList(searchCodeTest);
    }

    @PostMapping("update")
    public Result update(@RequestBody CodeTest codeTest){
        return codeTestService.updateCodeTest(codeTest);
    }

    @GetMapping("delete/{id}")
    public Result delete(@PathVariable Long id){
        return codeTestService.delCodeTest(id);
    }


}
