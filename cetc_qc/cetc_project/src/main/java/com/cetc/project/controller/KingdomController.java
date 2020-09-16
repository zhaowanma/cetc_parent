package com.cetc.project.controller;


import com.cetc.common.core.entity.Result;
import com.cetc.model.log.LogAnnotation;
import com.cetc.model.project.Kingdom;
import com.cetc.project.service.KingdomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/kingdom")
public class KingdomController {
    @Autowired
    private KingdomService kingdomService;
    @GetMapping("findAll")
    public Result findAll(){
        return  kingdomService.findAll();
    }
    @PostMapping("page")
    public Result page(@RequestBody Map<String,Integer> params){
        return kingdomService.page(params);
    }
    @PostMapping("add")
    @LogAnnotation(module = "添加领域")
    public Result add(@RequestBody Kingdom kingdom){
        return kingdomService.save(kingdom);
    }
    @PostMapping("update")
    @LogAnnotation(module = "更新领域")
    public Result update(@RequestBody Kingdom kingdom){
        return kingdomService.save(kingdom);
    }
    @GetMapping("findByName/{name}")
    public Result findByName(@PathVariable String name){
        return kingdomService.findByName(name);
    }
    @GetMapping("delete/{id}")
    @LogAnnotation(module = "删除领域")
    public Result delete(@PathVariable Long id){
        return kingdomService.delete(id);
    }

}
