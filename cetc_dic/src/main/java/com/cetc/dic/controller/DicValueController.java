package com.cetc.dic.controller;

import com.cetc.common.core.entity.Result;
import com.cetc.dic.service.DicValueService;
import com.cetc.model.dic.DicValue;
import com.cetc.model.log.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("dicValue")
@Api(tags = "字典数据控制层类")
public class DicValueController {

    @Autowired
    private DicValueService dicValueService;

    @PostMapping("queryPageAll/{dicId}")
    @ApiOperation("根据字典id分页查询字典数据接口")
    public Result queryPageAll(@PathVariable Long dicId, @RequestBody Map params) {
        return dicValueService.queryPageDicValues(dicId, params);
    }

    @PostMapping("saveDicValue")
    @ApiOperation("保存字典数据接口")
    @LogAnnotation(module = "新增字典数据")
    public Result saveDicValue(@RequestBody DicValue dicValue){
        return dicValueService.saveDicValue(dicValue);
    }

    @GetMapping("queryById/{id}")
    @ApiOperation("根据字典数据id查询字典数据接口")
    public Result queryById(@PathVariable Long id){
        return dicValueService.queryById(id);
    }
    @PutMapping("updateDicValue")
    @ApiOperation("修改字典数据接口")
    @LogAnnotation(module = "修改字典数据")
    public Result updateDicValue(@RequestBody DicValue dicValue){
        return dicValueService.updateById(dicValue);
    }
    @PostMapping("queryByKey")
    @ApiOperation("根据字典数据标签搜索字典数据接口")
    public Result queryByKey(@RequestBody DicValue dicValue){
        return dicValueService.queryByKey(dicValue);
    }

    @DeleteMapping("deleteById/{id}")
    @ApiOperation("根据字典数据id删除字典数据接口")
    @LogAnnotation(module = "删除字典数据")
    public Result deleteById(@PathVariable Long id){
        return dicValueService.deleteById(id);
    }

    @GetMapping("queryDicValuesByDicType/{dicType}")
    @ApiOperation("根据字典类型查询字典数据接口")
    public Result queryDicValuesByDicType(@PathVariable String dicType){
        return dicValueService.queryDicValuesByDicType(dicType);
    }
}
