package com.cetc.alm.controller;

import com.cetc.alm.service.SQAService;
import com.cetc.common.core.entity.Result;
import com.cetc.model.dic.DicValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("almSQA")
public class SQAController {

    @Autowired
    private SQAService sqaService;

    @PostMapping("findSQAInfo/{codeId}")
    public Result findSQAInfo(@RequestBody List<DicValue> dicValues,@PathVariable long codeId){
        return sqaService.getSqaINFO(dicValues,codeId);
    }


}
