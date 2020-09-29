package com.cetc.project.mapper;

import com.cetc.model.project.ProjectMonthCommit;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProjectMonthCommitDao {

    void save(ProjectMonthCommit projectMonthCommit);

    List<ProjectMonthCommit> findAll(long projectId);

    void delete(long id);

    ProjectMonthCommit findById(long id);

    void update(ProjectMonthCommit projectMonthCommit);

}
