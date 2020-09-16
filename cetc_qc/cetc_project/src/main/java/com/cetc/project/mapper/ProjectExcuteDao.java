package com.cetc.project.mapper;

import com.cetc.model.project.ProjectExcute;
import com.cetc.project.entities.SearchProjectExcute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProjectExcuteDao {

    void addProjectExcute(ProjectExcute projectExcute);
    List<ProjectExcute> findProjectExcuteByProject(@Param("projectId") Long projectId);
    void updateProjectExcute(ProjectExcute projectExcute);
    void deleteOne(@Param("id") Long id);
    void deleteProjectExcute(SearchProjectExcute searchProjectExcute);
}
