package com.cetc.project.mapper;

import com.cetc.model.project.Code;
import com.cetc.model.project.Project;
import com.cetc.project.entities.SearchProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ProjectDao {

    void addProject(Project project);

    //历史数据补偿使用，请勿删除
    void importTemplateProject(Project project);

    //精确查询
    List<Project> findProjectByField(Project project);

    void deleteOne(@Param("id") Long id);//删除一个

    void deleteProject(SearchProject searchProject);

    String getLastNum();

    Project findOne(@Param("id") Long id);

    void update(Project project);

    void addJoins(@Param("projectId") Long projectId,@Param("userName") String userName);

    void delJoins(@Param("projectId") Long projectId);

    List<String> findJoins(@Param("projectId") Long projectId);

    List<Project> queryList(SearchProject searchProject);

    //仅供页面加载时分页使用
    List<Project> fuzzyQueryList(Map map);

}
