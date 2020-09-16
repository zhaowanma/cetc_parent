package com.cetc.project.service;

import com.cetc.common.core.entity.Result;
import com.cetc.model.project.ProjectExcute;
import com.cetc.project.entities.SearchProjectExcute;

public interface ProjectExcuteService {

    Result addProjectExcute(ProjectExcute projectExcute);
    Result findPageExcuteByProject(SearchProjectExcute searchExcute);
    Result updateProjectExcute(ProjectExcute projectExcute);
    Result deleteProjectExcute(Long id);

}
