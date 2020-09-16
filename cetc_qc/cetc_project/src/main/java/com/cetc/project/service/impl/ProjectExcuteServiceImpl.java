package com.cetc.project.service.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.project.Code;
import com.cetc.model.project.Project;
import com.cetc.model.project.ProjectExcute;
import com.cetc.project.entities.SearchProjectExcute;
import com.cetc.project.mapper.CodeDao;
import com.cetc.project.mapper.ProjectDao;
import com.cetc.project.mapper.ProjectExcuteDao;
import com.cetc.project.service.ProjectExcuteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProjectExcuteServiceImpl implements ProjectExcuteService {
    @Autowired
    private ProjectExcuteDao projectExcuteDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private CodeDao codeDao;
    @Override
    public Result addProjectExcute(ProjectExcute projectExcute) {
        Project project = projectDao.findOne(projectExcute.getProjectId());
        Code code = codeDao.findOne(project.getParentId());
        projectExcute.setProject(project.getName());
        projectExcute.setCode(code.getValue());
        projectExcute.setCodeId(code.getId());
        projectExcute.setKingdom(code.getKingdom());
        projectExcute.setKingdomId(code.getParentId());
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date current = new Date();
        projectExcute.setCreateBy(principal);
        projectExcute.setCreateDate(current);
        projectExcute.setUpdateBy(principal);
        projectExcute.setUpdateDate(current);
        projectExcuteDao.addProjectExcute(projectExcute);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    @Override
    public Result findPageExcuteByProject(SearchProjectExcute searchExcute) {
        PageHelper.startPage(searchExcute.getPageNum(),searchExcute.getPageSize());
        List<ProjectExcute> projectExcuteList = projectExcuteDao.findProjectExcuteByProject(searchExcute.getProjectId());
        PageInfo<ProjectExcute> pageInfo = new PageInfo<>(projectExcuteList);
        return new Result(true, StatusCode.OK,"",pageInfo);
    }

    @Override
    public Result updateProjectExcute(ProjectExcute projectExcute) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projectExcute.setUpdateDate(new Date());
        projectExcute.setUpdateBy(principal);
        projectExcuteDao.updateProjectExcute(projectExcute);
        return new Result(true,StatusCode.OK,"更新成功");
    }

    @Override
    public Result deleteProjectExcute(Long id) {
        projectExcuteDao.deleteOne(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
