package com.cetc.project.service.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.model.project.Code;
import com.cetc.model.project.Project;
import com.cetc.project.mapper.CodeDao;
import com.cetc.project.mapper.ProjectDao;
import com.cetc.project.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CodeDao codeDao;

    @Autowired
    private ProjectDao projectDao;
    @Override
    public Result countDash() {
        Code code = new Code();
        code.setZy(true);
        List<Code> codes = codeDao.findCodeByField(code);
        Project project = new Project();
        project.setZy(true);
        List<Project> projects = projectDao.findProjectByField(project);

        return null;
    }
}
