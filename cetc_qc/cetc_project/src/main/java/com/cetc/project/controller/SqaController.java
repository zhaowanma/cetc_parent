package com.cetc.project.controller;

import com.cetc.common.core.entity.Result;
import com.cetc.model.dic.DicValue;
import com.cetc.model.project.SQA;
import com.cetc.project.service.SqaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sqa")
public class SqaController {

    @Autowired
    private SqaService sqaService;

    /**
     * 根据codeid分页查询对应SQA
     * @param params
     * @return
     */
    @PostMapping("pageByParent")
    public Result pageByParent(@RequestBody Map<String,Object> params){
        return sqaService.pageByParent(params);
    }

    /**
     * 分页查询
     * @param params
     * @return
     */
    @PostMapping("pageShow")
    public Result pageShow(@RequestBody Map<String,Object> params) throws ParseException {
        return sqaService.pageShow(params);
    }

    /**
     * 获取当前总清单序号（最大数加一）
     * @return
     */
    @GetMapping("getMaxIndex")
    public Result getMaxIndex(){
        return sqaService.getMaxIndex();
    }

    /**
     * 添加SQA id: null (新增) 否则 ： 修改
     * @param sqa
     * @return
     */
    @PostMapping("add")
    public Result add(@RequestBody SQA sqa){
        return sqaService.add(sqa);
    }

    /**
     * 根据Id删除SQA
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable Long id){
        return sqaService.delete(id);
    }

    /**
     * 根据年份查询SQA问题数
     * @param yesrs
     * @return
     */
    @PostMapping("/getByYear")
    public Result getByYear(@RequestBody List<Date> yesrs) throws ParseException {
        return sqaService.getByYear(yesrs);
    }

    /**
     * 获取各个领域对应令号下的SQA数量
     * @return
     */
    @GetMapping("getSqaCountByKm")
    public Result getSqaCountByKm(){
        return sqaService.getSqaCountByKm();
    }

    /**
     * 根据领域获取对应令号下的SQA数量
     * @param domain
     * @return
     */
    @GetMapping("/getByKindDom/{domain}")
    public Result getByKindDom(@PathVariable String domain){
        return sqaService.getByKindDom(domain);
    }

    /**
     * 获取各个领域下的SQA数量返回Map<key,value>形式
     * @return
     */
    @GetMapping("AllSQASByKingDom")
    public Result AllSQASByKingDom(){
        return sqaService.AllSQASByKingDom();
    }

    /**
     * 根据年份获取各部门完成率
     * @param year
     * @return
     */
    @PostMapping("getSQACompletionRate/{year}")
    public Result getSQACompletionRate(@RequestBody List<DicValue> deptList, @PathVariable Date year){
        return sqaService.getSQACompletionRate(deptList,year);
    }
}
