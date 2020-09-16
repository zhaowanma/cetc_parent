package com.cetc.project.mapper;

import com.cetc.model.project.Code;
import com.cetc.model.project.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProjectVisitableDao {

    void insertProjectUsername(@Param("projectId") long projectId, @Param("username") String username);

    void deleteByUsernameAndProjectId(@Param("projectId") long projectId, @Param("username") String username);

    void deleteByProjectId(@Param("projectId") Long projectId);

    List<Project> findProjectsByUsername(String username);

    Project findProjectByProjectIdAndUsername(@Param("projectId") long projectId, @Param("username") String username);

    List<String> findUsernamesByProjectId(@Param("projectId") long projectId);
}
