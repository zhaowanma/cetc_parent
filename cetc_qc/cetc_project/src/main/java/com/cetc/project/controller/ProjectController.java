package com.cetc.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cetc.common.core.entity.Result;
import com.cetc.model.log.LogAnnotation;
import com.cetc.model.dic.DicValue;
import com.cetc.model.project.Project;
import com.cetc.project.entities.SearchCode;
import com.cetc.project.entities.SearchProject;
import com.cetc.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("add")
    @LogAnnotation(module = "添加项目")
    public Result add(@RequestBody Project project){
        return projectService.save(project);
    }

    @PostMapping("update")
    @LogAnnotation(module = "更新项目")
    public Result update(@RequestBody Project project){
        return projectService.save(project);
    }

    @PostMapping("queryList")
    public Result queryList(@RequestBody SearchProject searchProject){
        return projectService.queryList(searchProject);
    }

    @PostMapping("pageByParent")
    public Result pageByParent(@RequestBody SearchProject searchProject) {
        return projectService.pageByParent(searchProject);
    }

    @GetMapping("delProject/{id}")
    @LogAnnotation(module = "删除领域")
    public Result delProject(@PathVariable Long id){
       return projectService.delProject(id);
    }

    @GetMapping("getNum/{codeId}")
    public Result getNum(@PathVariable Long codeId) {
        return projectService.getNum(codeId);
    }

    @GetMapping("findByCode/{id}")
    public Result findByCode(@PathVariable Long id) {
        return projectService.findByCode(id);
    }

    @PostMapping("commitProject")
    @LogAnnotation(module = "提交项目申请")
    public Result commitProject(@RequestBody Map map) {
        return projectService.commitProjectApply(map);
    }

    @GetMapping("findProjectById/{id}")
    public Result findProjectById(@PathVariable long id) {
        return projectService.findProjectById(id);
    }

    @PostMapping("findProjectsByStatusReady")
    public Result findProjectsByStatusReady(@RequestBody SearchProject searchProject) {
        return projectService.findProjectsByStatusReady(searchProject);
    }
   //提供给流程引擎使用
    @PostMapping("updateProjectStatus")
    @LogAnnotation(module = "更新项目状态")
    public Result updateProjectStatus(@RequestBody Project project){
        return projectService.updateProjectStatus(project);
    }

    @GetMapping("delProjectAct/{id}")
    @LogAnnotation(module = "删除项目")
    public Result delProjectAct(@PathVariable Long id){
        return projectService.delProject(id);
    }

    /**
     * 项目管理树的项目数据，统计总项目，我的项目，在研项目，我的在研项目
     * @return
     */
    @GetMapping("findProjectData")
    public Result findProjectData() {
        return projectService.findProjectData();
    }


    /**
     * 《Tree项目管理节点》按月份统计项目趋势图
     *
     * @return
     */
    @PostMapping("countOfMonth")
    public Result countOfMonth(@RequestBody Map map) {
        return projectService.countOfMonth(map);
    }

    /**
     * 《Tree项目管理节点》修改项目的在研状态
     */
    @PostMapping("handleProjectIsZy")
    public Result handleProjectIsZy(@RequestBody Project project){
        return projectService.handleProjectIsZy(project);
    }



    @PostMapping("updateProjectAndJoins")
    public Result updateProjectAndJoins(@RequestBody Project project){
       return projectService.updateProjectAndJoins(project);
    }

    @PostMapping("setVisual")
    public Result setVisual(@RequestBody Map<String,Object> map){
        Object obj = map.get("project");
        String jsonstr = JSON.toJSONString(obj);
        Project project = JSONObject.parseObject(jsonstr, Project.class);
        return projectService.setVisual(project,(List<String>)map.get("visual"));
    }
    @GetMapping("findVisual/{projectId}")
    public Result findVisual (@PathVariable Long projectId) {
        return projectService.findVisual(projectId);
    }
    /**
     * 根据领域查询项目
     *
     * @param kingdomId
     * @return
     */
    @GetMapping("selectProjectByKingdomId/{kingdomId}")
    public Result selectProjectByKingdomId(@PathVariable Long kingdomId) {
        return projectService.selectProjectByKingdomId(kingdomId);
    }

    /**
     * 默认查询各领域下的项目数量
     * @return
     */
    @GetMapping("getProjectCountBykingdom")
    public Result getProjectCountBykingdom(){
        return projectService.getProjectCountBykingdom();
    }


    /**
     * 根据领域查询各测试级别下的项目数量
     * @param dicValueLis
     * @param kingdomId
     * @return
     */
    @PostMapping("selectProjectByKingdomIdAndTestGrade/{kingdomId}")
    public Result selectProjectByKingdomIdAndTestGrade(@RequestBody List<DicValue> dicValueLis,@PathVariable Long kingdomId){
        return projectService.selectProjectByKingdomIdAndTestGrade(dicValueLis,kingdomId);
    }

    @GetMapping("checkRole/{id}")
    public Result checkRole(@PathVariable long id){
        return projectService.checkRole(id);
    }

    /**
     * 查询可用项目
     * @return
     */
    @GetMapping("findReadyProjectByParentId/{codeId}")
    public Result findReadyProjectByParentId(@PathVariable Long codeId){
        return projectService.findReadyProjectByParentId(codeId);
    }

    /**
     * 项目管理主页查询创建的项目、参与的项目、在项目中担任测试负责人用
     * @param searchProject
     * @return
     */
    @PostMapping("countByParam")
    public Result countByParam(@RequestBody SearchProject searchProject){
        return projectService.countByParam(searchProject);
    }

    /**
     * 统计每月的项目、文档审查、sqa
     * @param searchProject
     * @return
     */
    @PostMapping("countProjectComprehensiveOfMonth")
    public Result countProjectComprehensiveOfMonth(@RequestBody SearchProject searchProject){
        return projectService.countProjectComprehensiveOfMonth(searchProject);
    }
    /**
     * 默认查询各测试级别下的项目数量
     * @return
     */
    @PostMapping("countProjectOfTestGrade")
    public Result countProjectOfTestGrade(@RequestBody SearchProject searchProject){
        return projectService.getProjectCountByTestGrade(searchProject);
    }

    @PostMapping("hasProject")
    public Result hasPorject(@RequestBody Project project){
        return projectService.hasProject(project);
    }

    @PostMapping("countOfWeek")
    public Result countOfWeek(@RequestBody SearchProject searchProject){
        return projectService.countOfWeek(searchProject);
    }




}
