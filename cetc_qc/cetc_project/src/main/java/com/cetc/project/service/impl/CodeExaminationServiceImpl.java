package com.cetc.project.service.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.project.CodeExamination;
import com.cetc.project.entities.SearchExamination;
import com.cetc.project.mapper.CodeExaminationDao;
import com.cetc.project.service.CodeExaminationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CodeExaminationServiceImpl implements CodeExaminationService {
    @Autowired
    private CodeExaminationDao examinationkDao;
    @Override
    public Result addSpotCheck(CodeExamination codeExamination) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date current = new Date();
        codeExamination.setCreateBy(principal);
        codeExamination.setCreateDate(current);
        codeExamination.setUpdateBy(principal);
        codeExamination.setUpdateDate(current);
        examinationkDao.add(codeExamination);
        return new Result(true, StatusCode.OK,"保存成功");
    }

    @Override
    public Result deleteOne(Long id) {
        examinationkDao.deleteOne(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    @Override
    public Result pageList(SearchExamination searchExamination) {
        PageHelper.startPage(searchExamination.getPageNum(),searchExamination.getPageSize());
        List<CodeExamination> spotChecks = examinationkDao.queryList(searchExamination);
        PageInfo<CodeExamination> info = new PageInfo<>(spotChecks);
        return new Result(true,StatusCode.OK,"",info);
    }

    @Override
    public Result updateSpotCheck(CodeExamination codeExamination) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        codeExamination.setUpdateDate(new Date());
        codeExamination.setUpdateBy(principal);
        examinationkDao.update(codeExamination);
        return new Result(true,StatusCode.OK,"更新成功");
    }
}
