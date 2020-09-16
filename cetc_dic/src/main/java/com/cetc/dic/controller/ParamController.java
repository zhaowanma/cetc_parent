package com.cetc.dic.controller;

import com.cetc.common.core.entity.Result;
import com.cetc.dic.service.ParamService;
import com.cetc.model.dic.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("params")
@Api(tags = "系统参数控制层类")
public class ParamController {

    @Autowired
    private ParamService paramService;

    @PostMapping("findPageParams")
    @ApiOperation("分页查询系统参数接口")
    public Result findPageParams(@RequestBody Map params){
        return paramService.findPageParams(params);
    }

    @PostMapping("saveParam")
    @ApiOperation("保存系统参数接口")
    public Result saveParam(@RequestBody Param param){
        return paramService.saveParam(param);
    }

    @GetMapping("queryById/{id}")
    @ApiOperation("根据id查询系统参数接口")
    public Result queryById(@PathVariable Long id){
        return paramService.queryById(id);
    }

    @PutMapping("updateById")
    @ApiOperation("根据id修改系统参数接口")
    public Result updateById(@RequestBody Param param){
        return paramService.updateById(param);
    }

    @DeleteMapping("deleteById/{id}")
    @ApiOperation("根据id删除系统参数接口")
    public Result deleteById(@PathVariable Long id){
        return paramService.deleteById(id);
    }

    @GetMapping("queryByKey/{key}")
    @ApiOperation("根据系统参数键名查询系统参数接口")
    public Result queryByKey(@PathVariable String key){
        return paramService.queryByKey(key);
    }
}
