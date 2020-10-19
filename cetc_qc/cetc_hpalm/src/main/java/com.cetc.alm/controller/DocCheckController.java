package com.cetc.alm.controller;

import com.cetc.alm.service.DocCheckService;
import com.cetc.common.core.entity.Result;
import com.cetc.model.project.DocumentCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("docCheck")
@RestController
public class DocCheckController {

    @Autowired
    private DocCheckService docCheckService;

    @PostMapping("docCheckToAlm")
    public Result docCheckToAlm(@RequestBody Map map){
        return docCheckService.docCheckToAlm(map);
    }


}
