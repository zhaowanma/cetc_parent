package com.cetc.dic.controller;

import com.cetc.common.core.entity.Result;
import com.cetc.dic.service.DicService;
import com.cetc.model.dic.Dictionary;
import com.cetc.model.log.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("dic")
@Api(tags = "字典控制层类")
public class DicController {
    @Autowired
    private DicService dicService;

    @PostMapping("saveDic")
    @ApiOperation("新增字典接口")
    @LogAnnotation(module = "新增字典")
    public Result saveDic(@RequestBody Dictionary dictionary){
        return dicService.saveDic(dictionary);
    }

    @PostMapping("queryPageDics")
    @ApiOperation("分页查询字典接口")
    public Result queryPageDics(@RequestBody Map params){
        return dicService.queryAllDics(params);
    }

    @GetMapping("queryDicById/{id}")
    @ApiOperation("根据字典id查询字典接口")
    public Result queryDicById(@PathVariable Long id){
        return dicService.queryDicById(id);
    }

    @GetMapping("queryByDicName/{dicName}")
    @ApiOperation("根据字典名称查询字典接口")
    public Result queryByDicName(@PathVariable String dicName){
        return dicService.queryByDicName(dicName);
    }

    @GetMapping("queryByDicType/{dicType}")
    @ApiOperation("根据字典类型查询字典接口")
    public Result queryByDicType(@PathVariable String dicType){
        return dicService.queryByDicType(dicType);
    }

    @PutMapping("updateDicById")
    @LogAnnotation(module = "修改字典")
    @ApiOperation("修改字典接口")
    public Result updateDicById(@RequestBody Dictionary dictionary){
        return dicService.updateDic(dictionary);
    }

    @PostMapping("fuzzyQueryDic")
    @ApiOperation("模糊查询字典接口")
    public Result fuzzyQueryDic(@RequestBody Map params){
        return dicService.fuzzyQueryDic(params);
    }
    @DeleteMapping("deleteDicById/{dicId}")
    @LogAnnotation(module = "删除字典")
    @ApiOperation("删除字典接口")
    public Result deleteDicById(@PathVariable Long dicId){
        return dicService.deleteDic(dicId);
    }
}
