package com.cetc.project.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.project.ProjectExcute;
import com.cetc.project.entities.SearchProjectExcute;

import java.util.Map;

public interface ProjectExcuteService {

    Result addProjectExcute(ProjectExcute projectExcute);

    Result findPageExcuteByMonthCommit(Map map);

    Result updateProjectExcute(ProjectExcute projectExcute);

    Result deleteProjectExcute(Long id);

}
