package com.cetc.project.controller;

import com.cetc.common.core.entity.Result;
import com.cetc.model.log.LogAnnotation;
import com.cetc.model.project.Code;
import com.cetc.project.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("code")
public class CodeController {

    @Autowired
    private CodeService codeService;

    /**
     * 令号管理新增令号
     */
    @PostMapping("add")
    @LogAnnotation(module = "新增令号")
    public Result add(@RequestBody Code code) {
        return codeService.save(code);
    }


    /**
     * 令号管理删除令号
     */
    @DeleteMapping("delete/{id}")
    @LogAnnotation(module = "删除令号")
    public Result deleteOne(@PathVariable Long id) {
        return codeService.delete(id);
    }


    /**
     * 令号管理修改令号
     */
    @PostMapping("update")
    @LogAnnotation(module = "修改令号")
    public Result update(@RequestBody Code code) {
        return codeService.save(code);
    }

    /**
     * 分页查询令号
     */
    @PostMapping("findPageCode")
    public Result findPageCode(@RequestBody Map map) {
        return codeService.findPageCode(map);
    }


    /**
     * 令号重复性校验
     */
    @PostMapping("checkCode")
    public Result checkCode(@RequestBody Code code) {
        return codeService.checkCode(code);
    }


    /**
     * 获取产品序号数
     *
     * @return
     */
    @GetMapping("getNum")
    public Result getNum() {
        return codeService.getNum();
    }


    /**
     * 以下为非令号管理页面
     * 《Tree项目管理节点》的统计令号数、令号在研数量
     */

    @GetMapping("findCodeData")
    public Result findCodeData() {
        return codeService.findDatas();
    }


    /**
     * 《Tree项目管理节点》的统计领域下的令号，饼图
     *
     * @return
     */
    @GetMapping("countOfKingdom")
    public Result countOfKingdom() {
        return codeService.countOfKingdom();
    }


    /**
     * 《Tree项目管理节点》按月份统计令号趋势图
     *
     * @return
     */
    @PostMapping("countOfMonth")
    public Result countOfMonth(@RequestBody Map map) {
        return codeService.countOfMonth(map);
    }


    /**
     * 《Tree子令号管理节点》分页查询已签收的令号
     */
    @PostMapping("findPageCodeWithReady")
    public Result findPageCodeWithReady(@RequestBody Map map) {
        return codeService.findPageCodeWithReady(map);
    }

    /**
     * 《Tree子令号管理节点》修改令号的在研状态
     */

    @PostMapping("handleCodeIsZy")
    @LogAnnotation(module = "修改令号的在研状态")
    public Result handleCodeIsZy(@RequestBody Code code){
            return codeService.handleCodeIsZy(code);
    }



    @PostMapping("/setLeader")
    @LogAnnotation(module = "设置总体负责人")
    public Result setLeader(@RequestBody Map<String, Object> map) {
        return codeService.setLeader(Long.valueOf(map.get("id").toString()), (String) map.get("leader"), (List<String>) map.get("visableList"));
    }

    /**
     * 根据令号id获取可视化人员
     *
     * @return
     */
    @GetMapping("findVisableUser/{codeId}")
    public Result findVisableUser(@PathVariable long codeId) {
        return codeService.findVisableUser(codeId);
    }


    @PostMapping("commitCodeApply")
    @LogAnnotation(module = "令号提交申请")
    public Result commitCodeApply(@RequestBody Map map) {
        return codeService.commitCodeApply(map);
    }


    /**
     * 此方法仅供工作流使用
     * @param code
     * @return
     */
    @PostMapping("updateCodeStatus")
    public Result updateCodeStatus(@RequestBody Code code) {
        return codeService.updateCode(code);
    }



    @GetMapping("findCodeById/{id}")
    public Result findCodeById(@PathVariable long id) {
        return codeService.findCodeById(id);
    }


    /**
     * 查询用户是否有可视权利
     *
     * @param id
     * @return
     */
    @GetMapping("checkRole/{id}")
    public Result checkRole(@PathVariable long id) {
        return codeService.checkRole(id);
    }



}





