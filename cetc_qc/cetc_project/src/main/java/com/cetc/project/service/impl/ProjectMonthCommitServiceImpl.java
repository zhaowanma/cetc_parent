package com.cetc.project.service.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.project.ProjectMonthCommit;
import com.cetc.project.mapper.ProjectMonthCommitDao;
import com.cetc.project.service.ProjectMonthCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ProjectMonthCommitServiceImpl implements ProjectMonthCommitService {

    @Autowired
    private ProjectMonthCommitDao projectMonthCommitDao;

    @Override
    public Result save(ProjectMonthCommit projectMonthCommit) {
        projectMonthCommit.setCreateTime(new Date());
        projectMonthCommitDao.save(projectMonthCommit);
        return new Result(true, StatusCode.OK,"ok");
    }

    @Override
    public Result update(ProjectMonthCommit projectMonthCommit) {
        projectMonthCommitDao.update(projectMonthCommit);
        return new Result(true, StatusCode.OK,"ok");
    }

    @Override
    public Result findAll(long projectId) {
        List<ProjectMonthCommit> projectMonthCommits = projectMonthCommitDao.findAll(projectId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (ProjectMonthCommit projectMonthCommit: projectMonthCommits) {
            String format = sdf.format(projectMonthCommit.getCommitTime());
            projectMonthCommit.setCommitTimeStr(format);
            String format1 = sdf2.format(projectMonthCommit.getCreateTime());
            projectMonthCommit.setCreateTimeStr(format1);
        }
        return new Result(true,StatusCode.OK,"ok",projectMonthCommits);
    }

    @Override
    public Result delete(long id) {
        projectMonthCommitDao.delete(id);
        return new Result(true, StatusCode.OK,"ok");
    }

    @Override
    public Result findById(long id) {
        ProjectMonthCommit byId = projectMonthCommitDao.findById(id);
        return new Result(true,StatusCode.OK,"ok",byId);
    }
}
