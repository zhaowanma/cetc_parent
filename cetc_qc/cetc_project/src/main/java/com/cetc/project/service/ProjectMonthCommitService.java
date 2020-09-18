package com.cetc.project.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.project.ProjectMonthCommit;

public interface ProjectMonthCommitService {

    Result save(ProjectMonthCommit projectMonthCommit);

    Result update(ProjectMonthCommit projectMonthCommit);

    Result findAll(long projectId);

    Result delete(long id);
}
