package com.cetc.project.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.dic.DicValue;
import com.cetc.model.project.Project;
import com.cetc.project.entities.SearchProject;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProjectService {


    /**
     * 保存项目
     * @param project
     */
    Result save(Project project);

    /**
     * 分页查询项目
     * @param searchProject
     * @return
     */
    Result pageByParent(SearchProject searchProject);

    /**
     * 获取测试类型
     * @param id
     * @return
     */
    Result getTestTypeList(Long id);

    /**
     * 新建项目获取
     * @param codeId
     * @return
     */
    Result getNum(Long codeId);

    /**
     * 删除项目
     * @param id
     * @return
     */
    Result delProject(Long id);

    /**
     * 条件查询
     * @param searchProject
     * @return
     */
    Result queryList(SearchProject searchProject);

    /**
     * 统计项目数、在研项目数、我的项目数、我的在研项目数
     * @return
     */
    Result findProjectData();

    /**
     * 项目月度统计趋势图
     * @param
     * @return
     */
    Result countOfMonth(Map map);


    Result handleProjectIsZy(Project project);

    Result findByCode(Long id);

    Result commitProjectApply(Map params);

    Result findProjectById(long id);

    Result updateProject(Project project);

    Result updateProjectAndJoins(Project project);

    Result setVisual(Project project, List<String> username);

    Result findVisual(Long projectId);

    Result selectProjectByKingdomId(Long kingdomId);

    Result getProjectCountBykingdom();

    Result getProjectCountByTestGrade(SearchProject searchProject);

    Result selectProjectByKingdomIdAndTestGrade(List<DicValue> dicValueLis, Long kingdomId);

    Result checkRole(long id);

    Result findReadyProjectByParentId(Long codeId);
    //根据创建人、测试负责人、参与人查询项目，用于项目主页查询统计
    Result countByParam(SearchProject searchProject);
    //查询每月的项目.SQA.文档审查数量
    Result countProjectComprehensiveOfMonth(SearchProject searchProject);
    //是否存在项目
    Result hasProject(Project project);
    //项目周创建统计（上周）
    Result countOfWeek(SearchProject searchProject);



    Result findProjectsByStatusReady(SearchProject searchProject);

}
